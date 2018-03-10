{
  val pluginVersion = System.getProperty("plugin.version")
  if(pluginVersion == null)
    throw new RuntimeException("""|The system property 'plugin.version' is not defined.
                                  |Specify this property using the scriptedLaunchOpts -D.""".stripMargin)
  else addSbtPlugin("com.github.crossroad0201" % """sbt-upload""" % pluginVersion)
}

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.6")

