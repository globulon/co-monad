lazy val info = new {
  val organization = "com.omd"
  val name         = "co-monads"
  val scalaVersion = "2.13.1"
}

lazy val versions = new {
  val cats          = "2.0.0"
  val `cats-mtl`    = "0.7.0"
  val kindProjector = "0.10.3"
  val scalatest     = "3.0.8"
  val scalacheck    = "1.14.0"
}

lazy val dependencies = new {
  val cats: Seq[ModuleID] = Seq(
    "org.typelevel" %% "cats-core"     % versions.cats,
    "org.typelevel" %% "cats-testkit"  % versions.cats % Test,
    "org.typelevel" %% "cats-effect"   % versions.cats,
    "org.typelevel" %% "cats-mtl-core" % versions.`cats-mtl`
  ).map(_.withSources)
    .map(_.withJavadoc)

  val tests: Seq[ModuleID] = Seq(
    "org.scalactic"  %% "scalactic"  % versions.scalatest  % Test,
    "org.scalatest"  %% "scalatest"  % versions.scalatest  % Test,
    "org.scalacheck" %% "scalacheck" % versions.scalacheck % Test
  ).map(_.withSources)
    .map(_.withJavadoc)
}

lazy val commonSettings = Seq(
  organization := info.organization,
  scalaVersion := info.scalaVersion,
  scalacOptions ++= Seq(
    "-deprecation", // Emit warning and location for usages of deprecated APIs.
    "-explaintypes", // Explain type errors in more detail.
    "-feature", // Emit warning and location for usages of features that should be imported explicitly.
    "-language:existentials", // Existential types (besides wildcard types) can be written and inferred
    "-language:experimental.macros", // Allow macro definition (besides implementation and application)
    "-language:higherKinds", // Allow higher-kinded types
    "-language:implicitConversions", // Allow definition of implicit functions called views
    "-unchecked", // Enable additional warnings where generated code depends on assumptions.
    "-Xcheckinit", // Wrap field accessors to throw an exception on uninitialized access.
    "-Xfatal-warnings", // Fail the compilation if there are any warnings.
    "-Xlint:adapted-args", // Warn if an argument list is modified to match the receiver.
    "-Xlint:constant", // Evaluation of a constant arithmetic expression results in an error.
    "-Xlint:delayedinit-select", // Selecting member of DelayedInit.
    "-Xlint:doc-detached", // A Scaladoc comment appears to be detached from its element.
    "-Xlint:inaccessible", // Warn about inaccessible types in method signatures.
    "-Xlint:infer-any", // Warn when a type argument is inferred to be `Any`.
    "-Xlint:missing-interpolator", // A string literal appears to be missing an interpolator id.
    "-Xlint:nullary-override", // Warn when non-nullary `def f()' overrides nullary `def f'.
    "-Xlint:nullary-unit", // Warn when nullary methods return Unit.
    "-Xlint:option-implicit", // Option.apply used implicit view.
    "-Xlint:package-object-classes", // Class or object defined in package object.
    "-Xlint:poly-implicit-overload", // Parameterized overloaded implicit methods are not visible as view bounds.
    "-Xlint:private-shadow", // A private field (or class parameter) shadows a superclass field.
    "-Xlint:stars-align", // Pattern sequence wildcard must align with sequence component.
    "-Xlint:type-parameter-shadow", // A local type parameter shadows a type already in scope.
    "-Ywarn-dead-code", // Warn when dead code is identified.
    "-Ywarn-extra-implicit", // Warn when more than one implicit parameter section is defined.
    "-Ywarn-numeric-widen", // Warn when numerics are widened.
    "-Ywarn-unused:implicits", // Warn if an implicit parameter is unused.
    "-Ywarn-unused:imports", // Warn if an import selector is not referenced.
    "-Ywarn-unused:locals", // Warn if a local definition is unused.
    "-Ywarn-unused:params", // Warn if a value parameter is unused.
    "-Ywarn-unused:patvars", // Warn if a variable bound in a pattern is unused.
    "-Ywarn-unused:privates", // Warn if a private member is unused.
    "-Ywarn-value-discard", // Warn when non-Unit expression results are unused.
    "-Ybackend-parallelism",
    "8", // Enable paralellisation — change to desired number!
    "-Ycache-plugin-class-loader:last-modified", // Enables caching of classloaders for compiler plugins
    "-Ycache-macro-class-loader:last-modified" // and macro definitions. This can lead to performance improvements.
  ),
  addCompilerPlugin("org.typelevel" %% "kind-projector" % versions.kindProjector),
  resolvers ++= Seq(
    Resolver.typesafeRepo("releases"),
    Resolver.sonatypeRepo("releases"),
    // Only necessary for SNAPSHOT release
    Resolver.sonatypeRepo("snapshots"),
    "confluent" at "https://packages.confluent.io/maven/"
  )
)

lazy val conway = project
  .in(file("modules/conway"))

lazy val examples = project
  .in(file("modules/examples"))
  .settings(commonSettings)
  .settings(libraryDependencies ++= dependencies.cats ++ dependencies.tests)

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
