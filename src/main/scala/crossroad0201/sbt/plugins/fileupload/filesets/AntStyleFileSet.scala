package crossroad0201.sbt.plugins.fileupload.filesets

import fileupload.FileSet
import io.github.azagniotov.matcher.AntPathMatcher
import sbt.File

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

case class AntStyleFileSet(
  baseDir: File,
  includes: List[String] = List("**/*"),
  excludes: List[String] = List()
) extends FileSet {
  def includes(patterns: String*): AntStyleFileSet = copy(includes = patterns.map { p => s"${baseDir.getPath}/$p" }.toList)
  def excludes(patterns: String*): AntStyleFileSet = copy(excludes = patterns.map { p => s"${baseDir.getPath}/$p" }.toList)

  override def listFiles = {
    val pathPatcher = new AntPathMatcher.Builder().build()

    @tailrec
    def isMatch(patterns: List[String], file: File): Boolean = {
      patterns match {
        case Nil => false
        case x::xs =>
          //println(s"  ${file.getPath} / $x -> ${pathPatcher.isMatch(x, file.getPath)}")
          if (pathPatcher.isMatch(x, file.getPath))
            true
          else
            isMatch(xs, file)
      }
    }

    @tailrec
    def findFiles(files: List[File], matchedFiles: ListBuffer[File]): Seq[File] = {
      files match {
        case Nil => matchedFiles
        case x::xs =>
          //println(s"${x.getAbsolutePath} -> ${x.isFile} ${!isMatch(excludes, x)} ${isMatch(includes, x)}")
          if (x.isFile && !isMatch(excludes, x) && isMatch(includes, x)) matchedFiles += x

          findFiles(
            if (x.isDirectory) x.listFiles().toList ++ xs else xs,
            matchedFiles
          )
      }
    }

    findFiles(baseDir.listFiles().toList, ListBuffer())
  }
}
