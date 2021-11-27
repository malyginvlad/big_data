import org.apache.spark.sql._
import org.apache.spark.sql.functions._

object Main {
    def main(args: Array[String]): Unit = {
      // make spark session
      val spark = SparkSession.builder()
        .config("spark.driver.bindAddress", "127.0.0.1")
        .master("local[*]")
        .appName("hw4")
        .getOrCreate()

      def calcIdf(df: Long, tf: Long): Double =
        math.log((df.toDouble + 1) / (tf.toDouble + 1))

      val df = spark.read
        .option("header", "true")
        .option("inferSchema", "true")
        .csv("data/tripadvisor_hotel_reviews.csv")
        .withColumn("Review", lower(col("Review")))
        .withColumn("Review", regexp_replace(col("Review"), "[^a-z0-9 ]", " "))
        .withColumn("Review", regexp_replace(col("Review"), "\\s+", " "))
        .withColumn("Review", trim(col("Review")))
        .withColumn("Review", split(col("Review"), " "))
        .withColumn("review_id", monotonically_increasing_id())

      val df_review = df
        .select(
          col("review_id"),
          explode(col("Review")).as("word")
        )
      val tf_all = df_review
        .groupBy("review_id", "word")
        .agg(countDistinct("review_id") as "tf")

      val tf = df_review
        .groupBy("word")
        .agg(countDistinct("review_id") as "tf")
        .orderBy(desc("tf"))
        .limit(100)
        .cache()

      val df_count = df.count()
      val getIdf = udf{tf: Long => calcIdf(df_count, tf)}
      val idf = tf
        .withColumn("idf", getIdf(col("tf")))
        .drop("tf")
        .cache()

      val tfIdf = idf
        .join(tf_all, tf("word") ===  idf("word"), "left")
        .drop(idf("word"))
        .withColumn("tfIdf", col("tf") * col("idf"))
        .drop("tf", "idf")

      val results = tfIdf
        .groupBy("review_id").pivot("word").max("tfIdf")

      results.show()
    }
}
