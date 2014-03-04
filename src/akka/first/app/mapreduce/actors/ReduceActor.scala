package akka.first.app.mapreduce.actors

import scala.collection.immutable.Map
import akka.actor.Actor
import akka.first.app.mapreduce.MapData
import akka.first.app.mapreduce.ReduceData
import akka.first.app.mapreduce.WordCount

class ReduceActor extends Actor {
  def receive: Receive = {
    case MapData(dataList) =>
      sender ! reduce(dataList)
  }

  def reduce(words: IndexedSeq[WordCount]): ReduceData = ReduceData {
//    println(words.toString())
    words.foldLeft(Map.empty[String, Int]) {
      (index, words) =>
        if (index contains words.word)
          index + (words.word -> (index.get(words.word).get + 1))
        else
          index + (words.word -> 1)
    }
  }
}
