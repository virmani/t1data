package sugarchallenged

import sugarchallenged.parser.TsvParser
import org.scala_tools.time.Imports._
import scala.slick.session.Database
import sugarchallenged.models.Events
import scala.slick.driver.MySQLDriver.simple._

import Database.threadLocalSession

object Runner {
  def main(args: Array[String]) {

    val database = Database.forURL(
      "jdbc:mysql://localhost:3306/t1data",
      driver = "com.mysql.jdbc.Driver",
      user = "user",
      password = "pass"
    )

     database withSession {
      Events.ddl.drop
      Events.ddl.create
      TsvParser.parse(args(0), DateTimeZone.forID("America/Los_Angeles"), database)
    }
  }
}
