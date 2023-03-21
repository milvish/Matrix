@file:Suppress("UNUSED_PARAMETER")
/**
 * Пример
 *
 * Транспонировать заданную матрицу matrix.
 */
fun <E> transpose(matrix: Matrix<E>): Matrix<E> {
    if (matrix.width < 1 || matrix.height < 1) return matrix
    val result = createMatrix(height = matrix.width, width = matrix.height, e = matrix[0, 0])
    for (i in 0 until matrix.width) {
        for (j in 0 until matrix.height) {
            result[i, j] = matrix[j, i]
        }
    }
    return result
}

fun <E> rotate(matrix: Matrix<E>): Matrix<E> = TODO()

/**
 * Сложить две заданные матрицы друг с другом.
 * Складывать можно только матрицы совпадающего размера -- в противном случае бросить IllegalArgumentException.
 * При сложении попарно складываются соответствующие элементы матриц
 */
operator fun Matrix<Int>.plus(other: Matrix<Int>): Matrix<Int> {
    if (height != other.height || width != other.width) throw IllegalArgumentException()
    var sumMatrix = createMatrix(height,width,0)
    for (column in 0..width-1)
        for (row in 0..height-1)
            sumMatrix[row, column] = this[row, column]+other[row,column]
    return sumMatrix
}

/**
 * Инвертировать заданную матрицу.
 * При инвертировании знак каждого элемента матрицы следует заменить на обратный
 */
operator fun Matrix<Int>.unaryMinus(): Matrix<Int> {
    var unaryMatrix = createMatrix(height,width,0)
    for (column in 0..width-1)
        for (row in 0..height-1)
            unaryMatrix[row, column] = -this[row, column]
    return unaryMatrix
}

/**
 * Перемножить две заданные матрицы друг с другом.
 * Матрицы можно умножать, только если ширина первой матрицы совпадает с высотой второй матрицы.
 * В противном случае бросить IllegalArgumentException.
 * Подробно про порядок умножения см. статью Википедии "Умножение матриц".
 */
operator fun Matrix<Int>.times(other: Matrix<Int>): Matrix<Int> {
    if (height != other.width || width!=other.height) throw IllegalArgumentException()
    var timesMatrix = createMatrix(height, height, 0)
    for (column in 0..height-1)
        for (row in 0 .. other.width-1)
            for (cell in 0.. width-1)
                timesMatrix[row, column] += this[column,cell]*other[cell,row]
    return timesMatrix
}


/**
 * Целочисленная матрица matrix состоит из "дырок" (на их месте стоит 0) и "кирпичей" (на их месте стоит 1).
 * Найти в этой матрице все ряды и колонки, целиком состоящие из "дырок".
 * Результат вернуть в виде Holes(rows = список дырчатых рядов, columns = список дырчатых колонок).
 * Ряды и колонки нумеруются с нуля. Любой из спискоов rows / columns может оказаться пустым.
 *
 * Пример для матрицы 5 х 4:
 * 1 0 1 0
 * 0 0 1 0
 * 1 0 0 0 ==> результат: Holes(rows = listOf(4), columns = listOf(1, 3)): 4-й ряд, 1-я и 3-я колонки
 * 0 0 1 0
 * 0 0 0 0
 */
fun findHoles(matrix: Matrix<Int>): Holes {
    val rows = mutableListOf<Int>()
    val columns = mutableListOf<Int>()
    var lineFullHoles:Boolean

    for (row in 0 until matrix.height){
        lineFullHoles = true
        for (column in 0 until matrix.width){
            if (matrix[row, column] == 1)
            {
                lineFullHoles = false
                break
            }
        }
        if (lineFullHoles) rows.add(row)

    }

    for (column in 0 until matrix.width){
        lineFullHoles = true
        for (row in 0 until matrix.height){
            if (matrix[row, column] == 1){
                lineFullHoles =false
                break
            }
        }
        if (lineFullHoles) columns.add(column)


    }
    return Holes(rows, columns)
}

/**
 * Класс для описания местонахождения "дырок" в матрице
 */
data class Holes(val rows: List<Int>, val columns: List<Int>)

/**
 * Даны мозаичные изображения замочной скважины и ключа. Пройдет ли ключ в скважину?
 * То есть даны две матрицы key и lock, key.height <= lock.height, key.width <= lock.width, состоящие из нулей и единиц.
 *
 * Проверить, можно ли наложить матрицу key на матрицу lock (без поворота, разрешается только сдвиг) так,
 * чтобы каждой единице в матрице lock (штырь) соответствовал ноль в матрице key (прорезь),
 * а каждому нулю в матрице lock (дырка) соответствовала, наоборот, единица в матрице key (штырь).
 * Ключ при сдвиге не может выходить за пределы замка.
 *
 * Пример: ключ подойдёт, если его сдвинуть на 1 по ширине
 * lock    key
 * 1 0 1   1 0
 * 0 1 0   0 1
 * 1 1 1
 *
 * Вернуть тройку (Triple) -- (да/нет, требуемый сдвиг по высоте, требуемый сдвиг по ширине).
 * Если наложение невозможно, то первый элемент тройки "нет" и сдвиги могут быть любыми.
 */
fun canOpenLock(key: Matrix<Int>, lock: Matrix<Int>): Triple<Boolean, Int, Int> {
    for (row in 0 .. lock.height - key.height)
        for (column in 0 .. lock.width - key.width){
            var suitableKey = true

            for (rr in 0 until key.height)
                for (cc in 0 until key.width)
                    if (key[rr,cc] == lock[row + rr, column +cc]){
                        suitableKey = false
                    }
            if (suitableKey) return Triple(true, row, column)
        }
    return Triple(false,0,0)
}