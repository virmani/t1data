package sugarchallenged.utils

import org.scala_tools.time.Imports._

object Timestamp {
  def excelEpoch(dateTimeZone: DateTimeZone) = new DateTime(1900, 1, 1, 0, 0, dateTimeZone) - 2.days

  def fromExcel(excelTimestamp: String, dateTimeZone: DateTimeZone): DateTime = {
    val epoch = excelEpoch(dateTimeZone)
    val parts = excelTimestamp.split('.')
    val days = parts(0).toInt.days
    val seconds = ((if (parts.length == 2) ("0." + parts(1)) else "0").toFloat * 86400).toInt.seconds
    epoch + days + seconds
  }
}
