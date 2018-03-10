package crossroad0201.sbt.plugins.upload

import sbt.File
import scala.util.Try

case class UploadSet(
  dest: Destination,
  fileSet: FileSet
)

trait Destination {
  def getUploader: Uploader
}
trait Uploader {
  def upload(file: File): Try[Unit]
}

trait FileSet {
  def listFiles: Seq[File]
}
