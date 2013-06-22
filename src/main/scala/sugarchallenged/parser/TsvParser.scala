package sugarchallenged.parser

import org.scala_tools.time.Imports._
import sugarchallenged.utils.Timestamp

object TsvParser {
  def parse(inputFile: String, dateTimeZone: DateTimeZone) = {
    val source = scala.io.Source.fromFile(inputFile)(io.Codec("ISO-8859-1"))
    source.getLines foreach {
      line =>
        val fields = line.split('\t')
        if (fields(0) != "DATEEVENT") {
          val timestamp = Timestamp.fromExcel(fields(0), dateTimeZone)
          println(timestamp)
        }
    }
  }
}
