import sbt.Keys._
import sbt.{ Def, _ }
import sbtassembly.AssemblyKeys._
import sbtassembly.AssemblyPlugin.autoImport.assemblyOption
import sbtassembly.{ MergeStrategy, PathList }

//noinspection TypeAnnotation
object ProjectSettings {
  val generalSettings = Seq(
    organization := "ru.neoflex",
    version := "1.0",
    scalacOptions ++= CompileOptions.compileOptions212
//    resolvers += Repositories.Nexus.It.withAllowInsecureProtocol(true) //add internal repository (proxy) resolver
  )

  val generatedModuleSettings: Seq[Def.Setting[_]] = generalSettings ++
    Seq(scalacOptions --= CompileOptions.suppressWarnOptions)

  def assemblySettings = Seq(
    assembly / assemblyOption := (assemblyOption in assembly).value.copy(includeScala = false),
    assembly / assemblyMergeStrategy := {
      case PathList("module-info.class", xs @ _*)                                => MergeStrategy.discard
      case x if x.contains("module-info.class")                                  => MergeStrategy.first
      case "module-info.class"                                                   => MergeStrategy.discard
      case PathList("org", "apache", "spark", "unused", "UnusedStubClass.class") => MergeStrategy.first
      case x                                                                     => (assemblyMergeStrategy in assembly).value(x)
    }
  )

  // Include "provided" dependencies back to default run task
  val runLocalSettings = Seq(
    Compile / run := Defaults
      .runTask(
        fullClasspath in Compile,
        mainClass in (Compile, run),
        runner in (Compile, run)
      )
      .evaluated
  )
}
