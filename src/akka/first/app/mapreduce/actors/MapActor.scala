package akka.first.app.mapreduce.actors

import akka.actor.Actor
import scala.collection.mutable.ArrayBuffer
import akka.first.app.mapreduce.{MapData, WordCount}

class MapActor extends Actor {
  val STOP_WORDS_LIST = List("a", "am", "an", "and", "are", "as",
    "at", "be", "do", "go", "if", "in", "is", "it", "of", "on", "the",
    "to")
  val defaultCount: Int = 1

  def receive: Receive = {
    case message: String =>
      sender ! evaluateExpression(message)
  }

  def evaluateExpression(line: String): MapData = MapData {
    line.split( """\s+""").foldLeft(ArrayBuffer.empty[WordCount]) {
      (index, word) =>
        if (!STOP_WORDS_LIST.contains(word.toLowerCase)) {
          index += WordCount(word.toLowerCase, 1)
        } else {
          index
        }
    }
  }
}