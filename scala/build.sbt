lazy val info = new {
  val organization = "com.omd"
  val name         = "co-monads"
  val scalaVersion = "2.13.1"
}

lazy val versions = new {
  val cats          = "2.0.0"
  val `cats-mtl`    = "0.7.0"
  val kindProjector = "0.10.3"
}

lazy val dependencies = new {
  val cats: Seq[ModuleID] = Seq(
    "org.typelevel" %% "cats-core"     % versions.cats,
    "org.typelevel" %% "cats-testkit"  % versions.cats % Test,
    "org.typelevel" %% "cats-effect"   % versions.cats,
    "org.typelevel" %% "cats-mtl-core" % versions.`cats-mtl`
  )

  val tests: Seq[ModuleID] = Seq(
    "org.scalactic"           %% "scalactic"                      % versions.scalatest  % Test,
    "org.scalatest"           %% "scalatest"                      % versions.scalatest  % Test,
    "org.scalacheck"          %% "scalacheck"                     % versions.scalacheck % Test,
    "io.github.embeddedkafka" %% "embedded-kafka-schema-registry" % versions.confluent  % Test
  )

}

lazy val commonSettings = Seq(
  organization  := info.organization,
  scalaVersion  := info.scalaVersion,
  scalacOptions ++= Seq("-Xmax-classfile-name", "128"),
  scalacOptions ++= Seq(
    "-deprecation",
    "-encoding",
    "UTF-8",
    "-explaintypes",
    "-Yrangepos",
    "-feature",
    "-Xfuture",
    "-Ypartial-unification",
    "-language:higherKinds",
    "-language:existentials",
    "-language:implicitConversions",
    "-unchecked",
    "-Yno-adapted-args",
    "-Xlint:_,-type-parameter-shadow",
    "-Xsource:2.13",
    "-Ywarn-dead-code",
    "-Ywarn-inaccessible",
    "-Ywarn-infer-any",
    "-Ywarn-nullary-override",
    "-Ywarn-nullary-unit",
    "-Ywarn-numeric-widen",
    "-Ywarn-value-discard",
    "-Xfatal-warnings",
    "-Ywarn-unused:imports",
    "-Ywarn-unused:_,imports",
    "-opt-warnings",
    "-Xlint:constant",
    "-Ywarn-extra-implicit"
  ),
  addCompilerPlugin("org.typelevel" %% "kind-projector" % versions.kindProjector),
  resolvers ++= Seq(
    Resolver.typesafeRepo("releases"),
    Resolver.sonatypeRepo("releases"),
    // Only necessary for SNAPSHOT release
    Resolver.sonatypeRepo("snapshots"),
    "confluent" at "https://packages.confluent.io/maven/",
  )
)

lazy val conway = project
  .in(file("modules/conway"))

lazy val examples = project
  .in(file("modules/examples"))

lazy val root = project
  .in(file("."))
  .aggregate(examples, conway)
  .settings(
    name := info.name,
    addCommandAlias("checkFormat", ";scalafmtCheckAll;scalafmtSbtCheck"),
    addCommandAlias("format", ";scalafmtAll;scalafmtSbt"),
    addCommandAlias("update", ";dependencyUpdates;reload plugins;dependencyUpdates;reload return"),
    addCommandAlias("build", ";checkFormat;clean;test"),
    addCommandAlias("runApp", ";clean;app/runMain com.hbc.streams.prices.Engine")
  )
