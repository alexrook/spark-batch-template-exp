package ru.neoflex.okb.hdfs.settings

import org.apache.spark.sql.SaveMode
import pureconfig._
import pureconfig.configurable._

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object JobSettings {
  implicit val localDateConvert: ConfigConvert[LocalDate] = localDateConfigConvert(DateTimeFormatter.ISO_DATE)

  def apply(): JobSettings = {
    import pureconfig.generic.auto.exportReader
    val settings = ConfigSource.default.load[JobSettings] match {
      case Right(b) => b
      case Left(ex) =>
        throw new IllegalArgumentException(ex.prettyPrint())
    }
    settings
  }
}

case class JobSettings(
  appName: String,
  master: String,
  sparkLogLevel: String,
  sparkUiPort: String,
  output: OutputSettings)

case class OutputSettings(baseDirectory: String, writeMode: WriteMode, splitPartition: Option[Int])

sealed trait WriteMode {
  def saveMode: SaveMode
}
case object Append extends WriteMode {
  override def saveMode: SaveMode = SaveMode.Append
}

case object Overwrite extends WriteMode {
  override def saveMode: SaveMode = SaveMode.Overwrite
}
