@file:Suppress("UNUSED_PARAMETER")

import java.lang.IllegalArgumentException
import kotlin.random.Random


/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E

    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)

    operator fun set(cell: Cell, value: E)
}

/**
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> {
    if (height <= 0 || width <= 0) throw IllegalArgumentException()
    return MatrixImpl(height, width, e)
}

fun changeMatrix(matrix: Matrix<Int>):Matrix<Int>{
    val height = matrix.height
    val width = matrix.width
    var matrix2 = matrix
    var k = 1
    for (column in 0..width-1)
        for (row in 0..height-1){
            var i ="$row"
            var m ="$column"
            var n = i+m

            //matrix3.set(row,column,n.toInt())
            matrix2.set(row,column, k)
            k++
        }
    return matrix2
}

fun changeMatrixZeroOne(matrix: Matrix<Int>): Matrix<Int>{
    val height = matrix.height
    val width = matrix.width
    var matrix2 = matrix
    for (column in 0 .. width-1)
        for (row in 0.. height-1) {
            var i = Random.nextInt(0, 2)
            matrix2.set(row, column, i)
        }
    return  matrix2
}
/**
 * Реализация интерфейса "матрица"
 */

@Suppress("EqualsOrHashCode")
class MatrixImpl<E>(override val height: Int, override val width: Int, e: E)
    : Matrix<E> {

    private  val matrix: MutableList<MutableList<E>> = MutableList(height){MutableList<E>(width) {e} }
    //private  val matrix: MutableList<Int> = mutableListOf()



    override fun get(row: Int, column: Int): E = matrix[row][column]

    override fun get(cell: Cell): E = matrix[cell.row][cell.column]

    override fun set(row: Int, column: Int, value: E) {
        matrix[row][column] = value
    }

    override fun set(cell: Cell, value: E) {
        matrix[cell.row][cell.column]
    }

    override fun equals(other: Any?): Boolean{
        if (this == other) return true
        if (other == null) return false
        if (other !is Matrix<*>) return false
        if (height != other.height || width!=other.width) return false
        for (row in 0 .. height -1)
            for (column in 0 .. width-1)
                if (this[row, column] != other[row, column])
                    return false
        return true
    }

    //override fun toString(): String  = matrix.toString()
    override fun toString():String   {
        println("матрица [${height}x${width}]")
        var printMatrix = ""
        for (row in 0..height-1) {
            for (column in 0..width - 1)
                printMatrix += ("${matrix[row][column]}       ")
            printMatrix+= '\n'
        }
        return printMatrix
    }

}