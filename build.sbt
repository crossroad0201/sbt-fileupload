sbtPlugin := true

name := """sbt-fileupload"""
organization := "crossroad0201.sbt"
version := "0.1-SNAPSHOT"

scalacOptions    ++= Seq("-feature", "-deprecation")
// ^ publish
crossSbtVersions  := Seq("0.13.6", "1.0.0")

libraryDependencies += "com.amazonaws" % "aws-java-sdk-s3" % "1.11.292"
libraryDependencies += "io.github.azagniotov" % "ant-style-path-matcher" % "1.0.0"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1" % Test
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % Test

bintrayPackageLabels := Seq("sbt","plugin")
bintrayVcsUrl := Some("""git@github.com:crossroad0201/sbt-fileupload.git""")

initialCommands in console := """import fileupload._"""

scriptedLaunchOpts ++= Seq("-Xmx1024M", "-Dplugin.version=" + version.value)
