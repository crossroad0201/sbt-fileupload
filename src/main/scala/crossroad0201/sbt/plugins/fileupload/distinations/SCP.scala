package crossroad0201.sbt.plugins.fileupload.distinations

import java.io.FileInputStream
import java.security.KeyFactory

import fileupload.{ Destination, Uploader }
import org.apache.sshd.client.SshClient
import org.apache.sshd.client.session.ClientSession
import org.apache.sshd.common.config.keys.loader.pem.RSAPEMResourceKeyPairParser
import sbt.File

import scala.Console._
import scala.util.{ Failure, Success, Try }

case class SCP(
  host: String,
  port: Int,
  remoteDir: String,
  user: String,
  authentication: Authenticator
) extends Destination {
  override def getUploader = new Uploader {
    val sshClient = SshClient.setUpDefaultClient()
    sshClient.start()

    val sshSession = sshClient.connect(user, host, port).verify(10000).getSession
    authentication.setIdentityTo(sshSession)
    sshSession.auth().verify(10000)

    val scpClient = sshSession.createScpClient()

    override def upload(file: File, destPath: String) = {
      val toPath = s"$remoteDir/$destPath"

      print(s"Uploading ${file.getPath} to scp://$user@$host:$port$toPath...")
      val result = for {
        _ <- Try {
          scpClient.upload(file.toPath, destPath)
        }
      } yield ()

      result match {
        case Success(_) => println(s"${GREEN}Done.$RESET")
        case Failure(e) => println(s"${RED}Failed. $e$RESET")
      }

      result
    }

    override def close(): Unit = {
      sshSession.close()
      sshClient.close()
    }
  }
}
object SCP {
  def apply(host: String, remoteDir: String, user: String, password: String): SCP =
    SCP(host, 22, remoteDir, user, WithPassword(password))

}

sealed trait Authenticator {
  def setIdentityTo(session: ClientSession): ClientSession
}
case class WithPassword(password: String) extends Authenticator {
  override def setIdentityTo(session: ClientSession) = {
    session.addPasswordIdentity(password)
    session
  }
}
case class PublicKey(keyFile: File) extends Authenticator {
  override def setIdentityTo(session: ClientSession) = {
    session.addPublicKeyIdentity {
      RSAPEMResourceKeyPairParser.decodeRSAKeyPair(
        KeyFactory.getInstance("RSA"),
        new FileInputStream(keyFile),
        true
      )
    }
    session
  }
}