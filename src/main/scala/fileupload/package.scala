import sbt.File

import scala.language.implicitConversions

package object fileupload {

  type AmazonS3 = crossroad0201.sbt.plugins.fileupload.distinations.AmazonS3
  val AmazonS3 = crossroad0201.sbt.plugins.fileupload.distinations.AmazonS3

  type AntStyle =  crossroad0201.sbt.plugins.fileupload.filesets.AntStyleFileSet
  val AntStyle =  crossroad0201.sbt.plugins.fileupload.filesets.AntStyleFileSet

  implicit def fileToFileSet(file: File): FileSet = new FileSet {
    override val listFiles = Seq(file)
  }

  implicit def filesToFileSet(files: Seq[File]): FileSet = new FileSet {
    override val listFiles = files
  }

}
