package crossroad0201.sbt.plugins.fileupload.distinations

import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.PutObjectRequest
import fileupload.{Destination, Uploader}
import sbt.File

import scala.Console._
import scala.util.{Failure, Success, Try}

case class AmazonS3(
  bucketName: String,
  prefix: Option[String]
) extends Destination {
  override def getUploader = new Uploader {
    // TODO Specify AWS credential.
    // TODO Specify AWS profile.
    private lazy val s3Client = AmazonS3ClientBuilder.standard().build()

    override def upload(file: File) = {
      val key = prefix match {
        case Some(p) => s"$p/${file.getPath}"
        case None => file.getPath
      }

      print(s"Uploading ${file.getPath} to s3://$bucketName/$key...")
      val result = for {
        _ <- Try {
          s3Client.putObject {
            new PutObjectRequest(
              bucketName,
              key,
              file
            )
          }
        }
      } yield ()

      result match {
        case Success(_) => println(s"${GREEN}Done.$RESET")
        case Failure(e) => println(s"${RED}Failed. $e$RESET")
      }

      result
    }
  }
}
object AmazonS3 {
  def apply(bucketName: String): AmazonS3 = AmazonS3(bucketName, None)
  def apply(bucketName: String, prefix: String): AmazonS3 = AmazonS3(bucketName, Some(prefix))
}
