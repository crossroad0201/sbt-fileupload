# sbt File-Upload plugin

A simple sbt plugin for file upload to any destination.

## Usage

### Supported sbt version

This plugin supports sbt version 1.x and 0.13.x.

### Enable plugin

* Add to your `project/plugins.sbt`.
  ```scala
  addSbtPlugin("com.github.crossroad0201" % "sbt-fileupload" % VERSION)
  ```

* Enable `FileUploadPlugin`.


### Plugin configuration

* import `fileupload._`.

* Define upload files and destination via `fileSets` key.

### build.sbt example

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
        fileSet = AntStyle(file("target"))
          .includes(
            "**/*.jar"
          )
          .excludes(
            "streams/**"
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

`fileupload.AmazonS3(bucketName, prefix)`

## File sets

### File(s)

Specify upload file(s).

### Apache-Ant style file set

Upload matched file(s) under a specific base directory.

About the pattern for file name, see [here](https://ant.apache.org/manual/dirtasks.html#patterns). 


----

### Testing

Run `sbt test` for regular unit tests.

Run `sbt "^ publishLocal" && (cd src/sbt-test/sbt-fileupload/simple && sbt -Dplugin.version=0.1-SNAPSHOT fileUpload)` for plugin usage tests.

### Publishing

1. publish your source to GitHub
2. [create a bintray account](https://bintray.com/signup/index) and [set up bintray credentials](https://github.com/sbt/sbt-bintray#publishing)
3. create a bintray repository `sbt-plugins` 
4. update your bintray publishing settings in `build.sbt`
5. `sbt publish`
6. [request inclusion in sbt-plugin-releases](https://bintray.com/sbt/sbt-plugin-releases)
7. [Add your plugin to the community plugins list](https://github.com/sbt/website#attention-plugin-authors)
8. [Claim your project an Scaladex](https://github.com/scalacenter/scaladex-contrib#claim-your-project)
