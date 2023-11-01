import Dependencies._
import sbt.file

lazy val generateGitBuildInfo = taskKey[Seq[File]]("Generate Git head info")
val gitBuildInfoFileName      = "git_build.info"

lazy val sparkBatchTemplate = Project(id = "spark-batch-template", base = file("."))
  .enablePlugins(GitVersioning)
  .settings(name := "spark-batch-template")
  .settings(
    generateGitBuildInfo := {
      val log = streams.value.log
      val gitInfo = s"Released form '${git.gitCurrentBranch.value}' " +
        s"[${git.gitHeadCommit.value.getOrElse("-")}] at " +
        s"${git.gitHeadCommitDate.value.getOrElse("-")}"
      val file = (Compile / resourceManaged).value / gitBuildInfoFileName
      log.info(gitInfo)
      IO.write(file, gitInfo)
      Seq(file)
    },
    Compile / resourceGenerators += generateGitBuildInfo.taskValue,
    ProjectSettings.generalSettings,
    ProjectSettings.assemblySettings,
    ProjectSettings.runLocalSettings,
    assemblyJarName in assembly := s"${name.value}.jar",
    libraryDependencies ++= Spark.batchAll :+ Logging.slf4jApi :+ Common.pureConfig
  )
