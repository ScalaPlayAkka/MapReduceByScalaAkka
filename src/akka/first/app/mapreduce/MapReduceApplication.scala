package akka.first.app.mapreduce

import scala.collection.immutable.Map
import scala.collection.mutable.ArrayBuffer
import akka.actor.{actorRef2Scala, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import akka.first.app.mapreduce.actors.MasterActor
import scala.concurrent.{Future, Await}

sealed trait MapReduceMessage

case class WordCount(word: String, count: Int) extends
MapReduceMessage

case class MapData(dataList: ArrayBuffer[WordCount]) extends
MapReduceMessage

case class ReduceData(reduceDataMap: Map[String, Int]) extends
MapReduceMessage

case class Result() extends MapReduceMessage

object Main {

  def main(args: Array[String]) {
    val _system = ActorSystem("MapReduceApp")
    val master = _system.actorOf(Props[MasterActor], name = "master")
    implicit val timeout = Timeout(5 seconds)
    master ! "The quick brown fox tried to jump over the lazy dog and fell on the dog "
    master ! "Dog is man's best friend"
    master ! "Dog and Fox belong to the same family"

    Thread.sleep(5000)

    val future: Future[String] = (master ? Result()).mapTo[String]
    val result = Await.result(future, timeout.duration)
    println(s"$result")
    _system.shutdown()
  }
}