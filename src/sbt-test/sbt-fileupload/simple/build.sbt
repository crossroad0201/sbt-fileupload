import fileupload._

lazy val simple = (project in file("."))
  .enablePlugins(FileUploadPlugin)
  .settings(
    version := "0.1",
    scalaVersion := "2.12.1",
    assemblyJarName in assembly := "simple.jar",
    uploadSets := Seq(
      UploadSet(
        dest = AmazonS3("crossroad0201-sandbox", "upload/file"),
        fileSet = file("project/build.properties")
      ),
      UploadSet(
        dest = AmazonS3("crossroad0201-sandbox", "upload/files"),
        fileSet = Seq(
          file("test"),
          file("build.sbt")
        )
      ),
      UploadSet(
        dest = AmazonS3("crossroad0201-sandbox", "upload/ant"),
        fileSet = AntStyle(file("target"))
          .includes(
            "**/*.jar",
            "**/*.class"
          )
          .excludes(
            "streams/**"
          )
      )
    )
  )
