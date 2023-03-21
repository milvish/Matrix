fun main(args: Array<String>) {
    val matrix2 = createMatrix(5,2, 10)
    var matrix4 = changeMatrix(matrix2)

    val m1 = createMatrix(10, 4, 0)
    val m2 = changeMatrix(m1)
    println(matrix4)
    //println(m2)
    var matrix5 =transpose(matrix4)
    println(matrix5)

    println("Сумма матриц")
    var sumMat = matrix4.plus(matrix4)
    println(sumMat)

    println("Инвертированная матрица")
    var unaryMatrix = matrix4.unaryMinus()
    println(unaryMatrix)

    println("Перемножение матриц")
    var timesMatrix = matrix4.times(matrix5)
    println(timesMatrix)

    println("Матрица из 0 и 1")
    var zeroOneMatrix = changeMatrixZeroOne(m1)
    println(zeroOneMatrix)

    println ("Ключ")
    var keyMatrix = createMatrix(3,2, 0)
    var keyMatrix1 = changeMatrixZeroOne(keyMatrix)
    println(keyMatrix1)

    var result = canOpenLock(keyMatrix1, zeroOneMatrix)
    println(result)

    println("Нулевые строки и столбцы")
    var result1 = findHoles(keyMatrix1)
    println(result1)
}