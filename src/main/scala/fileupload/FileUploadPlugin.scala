package fileupload

import sbt._
import sbt.plugins.JvmPlugin

object FileUploadPlugin extends AutoPlugin {

  override def trigger = allRequirements
  override def requires = JvmPlugin

  object autoImport {
    val uploadSets = settingKey[Seq[UploadSet]]("The file(s) to be uploaded and the destination.")
    val fileUpload = taskKey[Unit]("Upload files.")
  }

  import autoImport._

  override lazy val projectSettings = Seq(
    uploadSets := Seq(),
    fileUpload := Def.task {
      for {
        uploadSet <- uploadSets.value
        file <- uploadSet.fileSet.listFiles
        destPath = if (uploadSet.keepDirStructure) file.getPath else file.getName
        uploader = uploadSet.dest.getUploader
      } yield for {
        _ <- uploader.upload(file, destPath)
        // FIXME Call Uploader#close()
      } yield uploader.close()
    }.value
  )

  override lazy val buildSettings = Seq()

  override lazy val globalSettings = Seq()
}
