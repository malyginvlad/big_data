package linear

import breeze.linalg.{DenseMatrix, DenseVector, inv}

class LinearRegression {

  var weight: DenseVector[Double] = DenseVector()

  def fit(X: DenseMatrix[Double], y: DenseVector[Double]): LinearRegression = {
    val n = X.rows
    val xTrain = DenseMatrix.horzcat(X, DenseMatrix.ones[Double](n,1))
    weight = inv(xTrain.t * xTrain) * xTrain.t * y
    this
  }

  def predict(X: DenseMatrix[Double]): DenseVector[Double] = {
    val n = X.rows
    val xTrain = DenseMatrix.horzcat(X, DenseMatrix.ones[Double](n,1))
    xTrain * weight
  }

}