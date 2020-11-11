package main.week7hw

import org.apache.log4j._
import org.apache.spark.SparkContext._
import org.apache.spark._
import org.apache.spark.sql.SparkSession

object wordCount3 {
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)
  def main(args: Array[String]) {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val session = SparkSession
      .builder
      .master("local[2]")
      .appName("wordCount2")
      .getOrCreate()
    val input = session.read.textFile("/Users/t891199/Downloads/TheHungerGames.txt")
    import session.implicits._
    val words = input
      .flatMap(x => x.split(" "))
      .map(x => x.replaceAll("\\W","") )
      .map(x => x.toLowerCase)

    val wordCounts = words.rdd.countByValue()
      .toSeq.sortBy(-_._2)
      .take(20)
    wordCounts.foreach(println)
  }
}


