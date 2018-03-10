package crossroad0201.sbt.plugins.upload

import sbt._
import sbt.plugins.JvmPlugin

object SbtUploadPlugin extends AutoPlugin {

  override def trigger = allRequirements
  override def requires = JvmPlugin

  object autoImport {
    val uploadSets = settingKey[Seq[UploadSet]]("FIXME about uploadSets.")
    val upload = taskKey[Unit]("FIXME about upload.")  // TODO Specify UploadSet name.
  }

  import autoImport._

  override lazy val projectSettings = Seq(
    uploadSets := Seq(),
    upload := Def.task {
      for {
        uploadSet <- uploadSets.value
        file <- uploadSet.fileSet.listFiles
      } yield for {
        _ <- uploadSet.dest.getUploader.upload(file)
      } yield ()
    }.value
  )

  override lazy val buildSettings = Seq()

  override lazy val globalSettings = Seq()
}
