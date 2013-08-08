package sugarchallenged

import sugarchallenged.parser.TsvParser
import org.scala_tools.time.Imports._

object Runner {
  def main(args: Array[String]) {
    TsvParser.parse(args(0), DateTimeZone.forID("America/Los_Angeles"))
  }
}
