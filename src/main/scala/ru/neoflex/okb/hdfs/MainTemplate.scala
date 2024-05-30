package ru.neoflex.okb.hdfs

import org.apache.spark.sql.SparkSession
import org.json4s.jackson.JsonMethods.pretty
import org.json4s.{ DefaultFormats, Extraction, Formats }
import org.slf4j.{ Logger, LoggerFactory }
import ru.neoflex.okb.hdfs.settings.JobConfigurable

object MainTemplate extends JobConfigurable {

  @transient val logger: Logger = LoggerFactory.getLogger(getClass)

  def main(args: Array[String]): Unit = {
    val settings                  = readJobSettings
    implicit val formats: Formats = DefaultFormats
    val jsonConfig                = pretty(Extraction.decompose(settings))
    logger.debug(s"Starting job with jobConfig: $jsonConfig")

    logger.error(s"==============ERROR Message========")
    logger.warn(s"==============WARN Message========")
    logger.info(s"==============INFO Message========")
    logger.debug(s"==============DEBUG Message========")
    logger.trace(s"==============TRACE Message========")

    implicit val spark: SparkSession = createSparkSession(settings)
    import spark.implicits._
    spark.sparkContext.parallelize(Seq(1, 2, 3)).toDF.show()

  }
}
