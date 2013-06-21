package sugarchallenged.utils

import org.scala_tools.time.Imports._

object Timestamp {
  def excelEpoch(dateTimeZone: DateTimeZone) = new DateTime(1900, 1, 1, 0, 0, dateTimeZone) - 2.days

  def fromExcel(excelTimestamp: String, dateTimeZone: DateTimeZone): DateTime = {
    val parts = excelTimestamp.split('.')
    val epoch = excelEpoch(dateTimeZone)
    epoch + parts(0).toInt.days + (("0." + parts(1)).toFloat * 86400).toInt.seconds
  }
}
