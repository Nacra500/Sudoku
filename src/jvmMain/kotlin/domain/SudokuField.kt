package domain

import java.util.*
import kotlin.math.sqrt

/**
 * structure standardizing how a sudoku field looks like.
 */
class SudokuField {
    val COLLIN: Int //defines how many columns and lines a field has
    val BOXPERCOLLIN: Int //defines how many boxes are locates in one line
    val TOTALNUMBEROFCELLS: Int //defines the total number of cells a field has
    var field: Array<IntArray>

    /**
     * creates an SudokuField from an integer array.
     * Throws an IllegalFieldSizeException if the given array cannot be a sudoku field
     *
     * @param field: array from which the Field should be generated
     */
    constructor(field: Array<IntArray>) {
        val y_size = field.size
        val x_size = field[0].size
        if (sqrt(y_size.toDouble()) % 1 != 0.0 ||
            Math.sqrt(x_size.toDouble()) % 1 != 0.0
        ) throw IllegalFieldSizeException("The field size has to be a number to the power of two.")
        if (y_size != x_size) throw IllegalFieldSizeException("The field x_size and y_size has to be the same.")
        COLLIN = x_size
        BOXPERCOLLIN = sqrt(COLLIN.toDouble()).toInt()
        TOTALNUMBEROFCELLS = COLLIN * COLLIN
        this.field = field
    }

    /**
     * creates a SudokuField from the number of collumns or lines it should have
     * Throws an IllegalFieldSizeException if the given number cannot be a sudoku field
     *
     * @param colLin: number if columns or lines the field should have
     */
    constructor(colLin: Int) {
        if (sqrt(colLin.toDouble()) % 1 != 0.0) throw IllegalFieldSizeException("The field size has to be a number to the power of two.")
        COLLIN = colLin
        BOXPERCOLLIN = Math.sqrt(COLLIN.toDouble()).toInt()
        TOTALNUMBEROFCELLS = COLLIN * COLLIN
        field = Array(colLin) { IntArray(colLin) }
        for (line in field) {
            Arrays.fill(line, EMPTY)
        }
    }

    /**
     * creates a new SudokuField from an existing one
     *
     * @param field: existing field
     */
    constructor(field: SudokuField) {
        val newField = Array(field.COLLIN) { IntArray(field.COLLIN) }
        for (x in 0 until field.COLLIN) {
            for (y in 0 until field.COLLIN) {
                newField[y][x] = field.getCellValue(x, y)
            }
        }
        COLLIN = field.COLLIN
        BOXPERCOLLIN = field.BOXPERCOLLIN
        TOTALNUMBEROFCELLS = field.TOTALNUMBEROFCELLS
        this.field = newField
    }

    fun getCellValue(x: Int, y: Int): Int {
        require(!(x >= COLLIN || x < 0 || y >= COLLIN || y < 0)) { "x and y must be whithin the boundaries of the field of " + COLLIN + "x" + COLLIN + "." }
        return field[y][x]
    }

    fun setValue(x: Int, y: Int, `val`: Int) {
        require(!(x >= COLLIN || x < 0 || y >= COLLIN || y < 0)) { "x and y must be whithin the boundaries of the field of " + COLLIN + "x" + COLLIN + "." }
        field[y][x] = `val`
    }

    /**
     *
     * @param x coordinate
     * @param y coordinate
     * @param val: value which should be compared
     * @return boolean if the value is the same in the field
     */
    fun compareValue(x: Int, y: Int, `val`: Int): Boolean {
        require(!(x >= COLLIN || x < 0 || y >= COLLIN || y < 0)) { "x and y must be whithin the boundaries of the field of " + COLLIN + "x" + COLLIN + "." }
        return field[y][x] == `val`
    }

    /**
     * prints the field in the console.
     */
    fun displayInConsole() {
        for (l in 0 until COLLIN) {
            val line = field[l]
            for (col in 0 until COLLIN) {
                val num = line[col]
                if (num != EMPTY) print("$num ") else print("  ")
                if ((col + 1) % BOXPERCOLLIN == 0) print("|")
            }
            print("\n")
            if ((l + 1) % BOXPERCOLLIN == 0) {
                for (i in 0 until COLLIN) {
                    print("__")
                }
                print("\n")
            }
        }
    }

    /**
     * deletes a value at a specified position
     *
     * @param x coordinate
     * @param y coordinate
     */
    fun removeNumber(x: Int, y: Int) {
        field[y][x] = EMPTY
    }

    companion object {
        const val EMPTY = 0 //defines the empty value of a cell

        /**
         * compares two sudokufields and returns it differences.
         *
         * @param field1
         * @param field2
         * @return List of SudokuCell containing the coordinates of the difference and the value of the second field.
         */
        fun compareFields(field1: SudokuField, field2: SudokuField): List<SudokuCell> {
            if (field1.COLLIN != field2.COLLIN) throw IllegalFieldSizeException("Both fields must have the same size to be compared.")
            val res: MutableList<SudokuCell> = ArrayList()
            val colLin = field1.COLLIN
            for (y in 0 until colLin) {
                for (x in 0 until colLin) {
                    val `val` = field2.getCellValue(x, y)
                    if (field1.getCellValue(x, y) != `val` && field1.getCellValue(x, y) != EMPTY) {
                        res.add(SudokuCell(x, y, `val`))
                    }
                }
            }
            return res
        }
    }
}
