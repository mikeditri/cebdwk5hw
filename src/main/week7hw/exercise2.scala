package main.week7hw

import java.nio.charset.CodingErrorAction

import org.apache.log4j._
import org.apache.spark.SparkContext._
import org.apache.spark._

import scala.io.{Codec, Source}


object rating_count {
  /** Load up a Map of movie IDs to movie names. */
  def loadMovieNames(): Map[Int, String] = {
    // Handle character encoding issues:
    implicit val codec = Codec("UTF-8")
    codec.onMalformedInput(CodingErrorAction.REPLACE)
    codec.onUnmappableCharacter(CodingErrorAction.REPLACE)
    // Create a Map of Ints to Strings, and populate it from u.item.
    var movieNames: Map[Int, String] = Map()
    val lines = Source.fromFile("ml-100k/u.item").getLines()
    for (line <- lines) {
      var fields = line.split('|')
      if (fields.length > 1) {
        movieNames += (fields(0).toInt -> fields(1))
      }
    }
    return movieNames
  }

  /** Our main function where the action happens */
  def main(args: Array[String]) {
    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf = new
        SparkConf().setMaster("local[*]").setAppName("PopularMovies").set("spark.driver.host", "localhost");
    // Create a SparkContext using every core of the local machine, named PopularMovies
    //alternative: val sc = new SparkContext("local[*]", "PopularMovies")
    val sc = new SparkContext(conf)
    // Read in each rating line
    val lines = sc.textFile("ml-100k/u.data")
    val line2 = lines.map((x => (x.split("\t")(1).toInt, (x.split("\t")(2).toInt,1))))
    //line2.collect().foreach(println)
    val movieCounts = line2.reduceByKey( (x, y) => (x._1 + y._1, x._2 + y._2))
      .map(x => (x._1,x._2._1/x._2._2))
    //movieCounts.collect().foreach(println)
    val flipped = movieCounts.map( x => (x._2, x._1) )
    //flipped.collect().foreach(println)
    val sortedMovies = flipped.sortByKey(ascending = false)
    val results = sortedMovies.collect()
    //results.foreach(println)
    var nameDict = sc.broadcast(loadMovieNames)
    val sortedMoviesWithNames = sortedMovies.map(x => (nameDict.value(x._2), x._1))
    sortedMoviesWithNames.take(10).foreach(println)

  }
}
//top 50 movies then map the names