package fileupload

import sbt.File
import scala.util.Try

trait Destination {
  def getUploader: Uploader
}
trait Uploader {
  def upload(file: File, destPath: String): Try[Unit]
  def close(): Unit
}
