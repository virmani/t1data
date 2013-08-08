package sugarchallenged

object EventType extends Enumeration {
  type EventType = Value
  val BG = Value(0)
  val Bolus = Value(1)
  val Basal = Value(2)
  val PumpChange = Value(3)
  val Food = Value(4)
}

import EventType.EventType
import org.joda.time.DateTime

case class Event(eventType: EventType, timestamp: DateTime, valueOption: Option[Float], commentOption: Option[String]) {
  override def toString: String = {
    "Event %s happened at %s with value=%s and comment was \"%s\"" format(eventType, timestamp, valueOption.getOrElse("Blip!"), commentOption.getOrElse(""))
  }
}
