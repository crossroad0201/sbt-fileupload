name := """sbt-upload"""
organization := "com.github.crossroad0201"
version := "0.1-SNAPSHOT"

scalacOptions    ++= Seq("-feature", "-deprecation")
crossSbtVersions  := Seq("0.13.6", "1.0.0")
sbtPlugin := true

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1" % "test"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

bintrayPackageLabels := Seq("sbt","plugin")
bintrayVcsUrl := Some("""git@github.com:com.github.crossroad0201/sbt-upload.git""")

initialCommands in console := """import crossroad0201.sbt.plugins.upload._"""

// set up 'scripted; sbt plugin for testing sbt plugins
scriptedLaunchOpts ++=
  Seq("-Xmx1024M", "-Dplugin.version=" + version.value)
