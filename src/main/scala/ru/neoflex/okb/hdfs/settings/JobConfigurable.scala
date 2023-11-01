package ru.neoflex.okb.hdfs.settings

import org.apache.spark.sql.SparkSession
import org.apache.spark.{ SparkConf, SparkContext }

trait JobConfigurable {
  def readJobSettings: JobSettings = JobSettings()

  def createSparkSession(settings: JobSettings): SparkSession = {
    val sparkCoreConf = new SparkConf()
      .setAppName(settings.appName)
      .setMaster(settings.master)
      .set("spark.ui.port", settings.sparkUiPort)
      .set("spark.ui.prometheus.enabled", "true")

    val sparkContext = SparkContext.getOrCreate(sparkCoreConf)
    sparkContext.setLogLevel(settings.sparkLogLevel)
    SparkSession.builder.config(sparkContext.getConf).getOrCreate()
  }

}
