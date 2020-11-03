package dataset
import org.apache.spark.sql.SparkSession
//
// Create Datasets of primitive type and tuple type ands show simple operations.
//
object Basic1 {
  def main(args: Array[String]) {
    val spark =
      SparkSession
        .builder()
        .master("local[2]")
        .appName("Dataset-Basic")
        .getOrCreate()
    import spark.implicits._
    // Create a tiny Dataset of integers
    val s = Seq(10, 11, 12, 13, 14, 15)
    val ds = s.toDS()
    ds.show()
    ds.dtypes.foreach(println(_))
    println("*** schema as if it was a DataFrame")
    ds.printSchema()
    println("*** values > 12")
    ds.where($"value" > 12).show()
    // This seems to be the best way to get a range that's actually a Seq and
    // thus easy to convert to a Dataset, rather than a Range, which isn't.
    val s2 = Seq.range(1, 100)
    println("*** size of the range")
    val tuples = Seq((1, "one", "un"), (2, "two", "deux"), (3, "three", "trois"))
    val tupleDS = tuples.toDS()
    println("*** Tuple Dataset types")
    // the tuple columns have unfriendly names, but you can use them to query
    println("*** filter by one column and fetch another")
  }
}