import sbt._

object Version {
  object Scala {
    val v212 = "2.12.11"
  }
  val spark      = "3.1.2"
  val slf4jApi   = "1.7.30"
  val pureConfig = "0.12.3"
}

//noinspection TypeAnnotation
object Dependencies {
  object Spark {
    val core     = "org.apache.spark" %% "spark-core" % Version.spark % Provided
    val sql      = "org.apache.spark" %% "spark-sql" % Version.spark % Provided
    val batchAll = Seq(core, sql)
  }

  object Logging {
    val slf4jApi = "org.slf4j" % "slf4j-api" % Version.slf4jApi
  }

  object Common {
    val pureConfig = "com.github.pureconfig" %% "pureconfig" % Version.pureConfig
  }

}
