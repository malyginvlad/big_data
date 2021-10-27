package linear

import breeze.io.CSVReader
import breeze.linalg._
import breeze.stats.mean

import java.io.{File, FileReader}


object Main {
  def main(args: Array[String]): Unit = {

    def getMSE(y: DenseVector[Double], pred: DenseVector[Double]): Double = {
      val diff = y - pred
      mean(diff * diff)
    }

    var data = CSVReader.read(new FileReader(new File("data/train.csv")), ',', skipLines = 1)
    data = data.takeWhile(line => line.nonEmpty && line.head.nonEmpty)
    val df = DenseMatrix.tabulate(data.length, data.head.length)((i, j) => data(i)(j).toDouble)

    val xTrain = df(::, 1 to 3)
    val yTrain = df(::, 0)
    val lrModel = new LinearRegression
    lrModel.fit(xTrain, yTrain)
    val pred = lrModel.predict(xTrain)
    println(s"mse on test = ${getMSE(yTrain, pred)}")
    csvwrite(new File("data/pred.csv"), pred.toDenseMatrix.t, ',')
  }
}