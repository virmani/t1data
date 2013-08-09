package sugarchallenged.models

import scala.slick.driver.MySQLDriver.simple._
import org.joda.time.DateTime
import com.github.tototoshi.slick.JodaSupport._
import scala.slick.driver.MySQLDriver.simple._

import scala.slick.session.Database
import Database.threadLocalSession


object EventType extends Enumeration {
  type EventType = Value
  val BG = Value(0)
  val Bolus = Value(1)
  val Basal = Value(2)
  val PumpChange = Value(3)
  val Food = Value(4)
}

import EventType.EventType

object Events extends Table[(Int, String, String, DateTime, Option[Float], Option[String])]("EVENTS") {

  // This is the primary key column
  def id = column[Int]("EVENT_ID", O.PrimaryKey, O.AutoInc)

  def deviceEventId = column[String]("DEVICE_EVENT_ID")

  def eventType = column[String]("EVENT_TYPE")

  def occuredAt = column[DateTime]("OCCURED_AT")

  def value = column[Option[Float]]("VALUE")

  def comment = column[Option[String]]("COMMENT", O.DBType("VARCHAR(1024)"))

  def * = id ~ deviceEventId ~ eventType ~ occuredAt ~ value ~ comment

  def idx = index("idx_device_event_id", deviceEventId, unique = true)

  def autoInc = deviceEventId ~ eventType ~ occuredAt ~ value ~ comment returning id

  def insert(event: Event) = {
    autoInc.insert(event.eventId, event.eventType.toString, event.timestamp, event.valueOption, event.commentOption)
  }
}

case class Event(eventId: String, eventType: EventType, timestamp: DateTime, valueOption: Option[Float], commentOption: Option[String]) {
  override def toString: String = {
    "Event(id: %s) %s happened at %s with value=%s and comment was \"%s\"" format(eventId, eventType, timestamp, valueOption.getOrElse("Blip!"), commentOption.getOrElse(""))
  }

  def save = {
    Events.insert(this)
  }
}
