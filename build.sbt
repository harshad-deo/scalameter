def commonSettings(nameStr: String) = Seq(
  name := nameStr,
  organization := "com.simianquant",
  scalaVersion := Settings.versions.scala,
  crossScalaVersions := Settings.versions.crossScala,
  incOptions := incOptions.value.withLogRecompileOnMacro(false),
  fork := true,
  autoAPIMappings := true,
  parallelExecution in Test := false,
  scalacOptions ++= {
    val common = List(
      // "-Ywarn-unused-import",
      "-deprecation",
      "-unchecked",
      "-explaintypes",
      "-encoding",
      "UTF-8",
      "-feature",
      "-Xlog-reflective-calls",
      "-Ywarn-inaccessible",
      "-Ywarn-infer-any",
      "-Ywarn-nullary-override",
      "-Ywarn-nullary-unit",
      "-Xfuture",
      "-Xelide-below",
      "900"
    )
    if (scalaVersion.value.startsWith("2.11")) {
      "-Xlint:_,-missing-interpolator" :: "-Ywarn-unused" :: common
    } else {
      "-Xlint:adapted-args,nullary-unit,inaccessible,nullary-override,infer-any,doc-detached,private-shadow," +
        "type-parameter-shadow,poly-implicit-overload,option-implicit,delayedinit-select,by-name-right-associative," +
        "package-object-classes,unsound-match,stars-align,constant" ::
        "-Ywarn-unused:imports,patvars,privates,locals" ::
        "-opt:l:method" ::
        common
    }
  },
  scalacOptions in (Compile, doc) ++= Seq(
    "-author",
    "-groups",
    "-implicits"
  )
)

lazy val core = project
  .in(file("core"))
  .settings(commonSettings("core"))
  .settings(
    libraryDependencies ++= Seq(
      "org.apache.commons" % "commons-math3" % Settings.versions.apacheCommonsMath,
      "org.ow2.asm" % "asm" % Settings.versions.asm,
      "org.apache.commons" % "commons-lang3" % Settings.versions.apacheCommonsLang,
      "org.scalatest" %% "scalatest" % Settings.versions.scalatest % "test",
      "org.scalacheck" %% "scalacheck" % Settings.versions.scalacheck % "test"
    )
  )

lazy val root = project
  .in(file("."))
  .settings(commonSettings("scalameter"))
  .dependsOn(core)
  .settings(
    libraryDependencies ++= Seq(
      "org.scala-lang.modules" %% "scala-xml" % Settings.versions.scalaXML,
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % Settings.versions.jackson,
      "org.scala-lang.modules" %% "scala-parser-combinators" % Settings.versions.scalaParser,
      "org.scala-tools.testing" % "test-interface" % Settings.versions.scalaTestInterface,
      "org.mongodb" %% "casbah" % Settings.versions.casbah,
      "commons-io" % "commons-io" % Settings.versions.commonsIO,
      "io.spray" %% "spray-json" % Settings.versions.spray,
      "org.scalatest" %% "scalatest" % Settings.versions.scalatest % "test"
    )
  )
