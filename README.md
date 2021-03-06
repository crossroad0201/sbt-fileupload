# sbt File-Upload plugin

A simple sbt plugin for file upload to any destinations.

**!!! Currently under development. !!!**

## Usage

### Supported sbt version

This plugin supports sbt version 1.x and 0.13.x.

### Add plugin

* Add to your `project/plugins.sbt`.
  ```scala
  resolvers += Resolver.url(
    "crossroad0201 Plugin Repository",
    url("https://dl.bintray.com/crossroad0201/sbt-plugins/")
  )(Resolver.ivyStylePatterns)

  addSbtPlugin("com.github.crossroad0201" % "sbt-fileupload" % VERSION)
  ```

### Plugin configuration

* import `fileupload._`.

* Enable `FileUploadPlugin` via `enablePlugins()`.

* Define upload files and destination via `uploadSets` key.
  * if `keepDirStructure` option is `false`, upload file(s) to just under the destination.(Default `true`)

```scala
import fileupload._

lazy val root = (project in file("."))
  .enablePlugins(FileUploadPlugin)
  .settings(
    uploadSets := Seq(
      // Specify file path.
      UploadSet(
        dest = AmazonS3("YOUR-S3-BUCKET", "files"),
        fileSet = Seq(
          file("foo/bar.txt")
        )
      ),
      // Specify Apache-Ant style file pattern.
      UploadSet(
        dest = AmazonS3("YOUR-S3-BUCKET", "modules"),
        keepDirStructure = false,
        fileSet = AntStyle(file("target"))
          .includes(
            "**/*.jar"
          )
      )
    )
  )
```

### Do upload!

Run `fileUpload` task.

```shell
sbt fileUpload
```

## Destinations

### Amazon S3

Use `fileupload.AmazonS3(bucketName, prefix)`.

AWS credential is working with [Default Credential Provider Chain](https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html). 

```scala
// Bucket only.
dest = AmazonS3("BUCKET_NAME")

// Bucket with prefix.
dest = AmazonS3("BUCKET_NAME", "PREFIX")
```

## File sets

### File(s)

Specify upload file(s).

```scala
// a file.
fileSet = file("foo")

// some files.
fileSet = Seq(
  file("foo"),
  file("bar")
)
```

### Apache-Ant style file set

Upload matched file(s) under a specific base directory.

About the pattern for file name, see [here](https://ant.apache.org/manual/dirtasks.html#patterns). 

```scala
fileSet = AntStyle(file("BASE_DIR"))
  .includes(
    "*.jar",
    "**/*.jar"
  )
  .excludes(
    "temp/**/*"
  )
```

## For maintainer

### Testing

Run `sbt test` for regular unit tests.

Run `sbt "^ publishLocal" && (cd src/sbt-test/sbt-fileupload/simple && sbt -Dplugin.version=0.1-SNAPSHOT fileUpload)` for plugin usage tests.

### Publishing to Bintray

1. Create account on [Bintray](https://bintray.com/signup/oss).
1. Change version in `build.sbt`.
1. Run sbt.
1. Run `bintrayChangeCredentials` task. (Prompt Bintray username and API key)
1. Run `^ publish` task. (Do not forget `^` for cross sbt build)

See [here](https://www.scala-sbt.org/1.0/docs/Bintray-For-Plugins.html) for more information.
