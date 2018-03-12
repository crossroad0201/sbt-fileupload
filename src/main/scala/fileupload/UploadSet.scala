package fileupload

case class UploadSet(
  dest: Destination,
  fileSet: FileSet,
  keepDirStructure: Boolean = true
)
