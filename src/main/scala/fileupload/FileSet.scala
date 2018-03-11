package fileupload

import sbt.File

trait FileSet {
  def listFiles: Seq[File]
}
