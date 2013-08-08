package sugarchallenged.parser

import org.scala_tools.time.Imports._
import sugarchallenged.utils.Timestamp
import sugarchallenged.{EventType, Event}

object TsvParser {
  val HEADER_COLUMN = "DATEEVENT"
  val POD_ACTIVATED_STRING = "Pod activated"

  private def isBGEvent(fields: Array[String]) = {
    fields(2) == "1"
  }

  private def isChangeEvent(fields: Array[String]) = {
    fields(2) == "2" &&
      (fields(10) == "0" || fields(10) == "1")
  }

  private def isBasalChangeEvent(fields: Array[String]) = {
    isChangeEvent(fields) && !fields(26).contains(POD_ACTIVATED_STRING)
  }

  private def isPodChangedEvent(fields: Array[String]) = {
    isChangeEvent(fields) && fields(26).contains(POD_ACTIVATED_STRING)
  }

  private def isBolusEvent(fields: Array[String]) = {
    fields(2) == "3" && (fields(10) == "0" || fields(10) == "1")
  }

  private def isFoodEvent(fields: Array[String]) = {
    fields(2) == "5"
  }

  def parse(inputFile: String, dateTimeZone: DateTimeZone) = {
    val source = scala.io.Source.fromFile(inputFile)(io.Codec("ISO-8859-1"))
    source.getLines foreach {
      line =>
        val fields = line.split('\t')
        if (fields(0) != HEADER_COLUMN) {
          val timestamp = Timestamp.fromExcel(fields(0), dateTimeZone)

          val event =
            if (isBGEvent(fields)) {
              Event(EventType.BG, timestamp, Some(fields(11).toFloat), None)
            } else if (isBasalChangeEvent(fields)) {
              Event(EventType.Basal, timestamp, Some(fields(20).toFloat), None)
            } else if (isPodChangedEvent(fields)) {
              Event(EventType.PumpChange, timestamp, None, None)
            } else if (isBolusEvent(fields)) {
              Event(EventType.Bolus, timestamp, Some(fields(20).toFloat), Some(fields(27) + "<br/>" + fields(36)))
            } else if (isFoodEvent(fields)) {
              Event(EventType.Food, timestamp, Some(fields(21).toFloat), None)
            }

          println(event)
        }
    }
  }
}
