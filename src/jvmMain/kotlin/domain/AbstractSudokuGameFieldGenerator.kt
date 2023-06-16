package domain

import java.util.*

/**
 * A Sudoku generator creates a uniquely solvable sudoku field with a specified number of missing cells.
 * The abstract generator privides some default implementations which are the same for every generator.
 */
abstract class AbstractSudokuGameFieldGenerator(protected var field: SudokuField) {
    // a two dimensional array of set containg all values which can possibly be set (and ot violating the rules) on the given coordinates
    protected var possibles: Array<Array<MutableSet<Int>>> = Array(field.COLLIN) {
                Array(field.COLLIN) {mutableSetOf(0) }
            }
    private val solutionSteps: Stack<SudokuCell> = Stack() // a stack containing all removal steps
    var numberOfRemovedCells = 0
        private set

    init {
        for (i in 0 until field.COLLIN) {
            for (j in 0 until field.COLLIN) {
                possibles[i][j] = HashSet<Int>()
            }
        }
    }
    /**
     * recursive algorthm which removes the specified number cells from the field.
     *
     * @param removeCount: defines how many cells should be removed
     */
    /**
     * algorthm which removes as many cells from the field as possible.
     */
    @JvmOverloads
    fun createSolvable(removeCount: Int = field.COLLIN * field.COLLIN) {
        var removeCount = removeCount
        if (removeCount <= 0) return
        val x =
            createRandom() // defines a randomizez starting column to make sure that not only the first columns are removed
        val y =
            createRandom() // defines a randomizez starting row to make sure that not only the first columns are removed
        var cellValue: Int = SudokuField.Companion.EMPTY
        for (i_x in 0 until field.COLLIN) { //for each column starting at the randomized startig column
            for (i_y in 0 until field.COLLIN) { //for each line starting at the randomized startig line
                val posX = Math.abs(x + i_x - (field.COLLIN - 1)) //used for the randomized shift
                val posY = Math.abs(y + i_y - (field.COLLIN - 1)) //used for the randomized shift
                if (field.getCellValue(
                        posX,
                        posY
                    ) != SudokuField.Companion.EMPTY
                ) { // if the cell value at the position is not empty
                    cellValue = field.getCellValue(posX, posY)
                    field.removeNumber(posX, posY) // clear cell
                    analyzeField() // find all possibles
                    if (checkForObvious(posX, posY, cellValue)) { // look if the removed cell can be found.
                        // if yes ...
                        removeCount--
                        numberOfRemovedCells++
                        createSolvable(removeCount) // try to remove the next number
                        solutionSteps.add(SudokuCell(posX, posY, cellValue))
                        return  // and stop iterating
                    } else {
                        field.setValue(posX, posY, cellValue) // refill cleared cell and try next one
                    }
                }
            }
        }
    }

    /**
     * helper function which provides all possibel values for all fields
     */
    private fun analyzeField() {
        for (y in 0 until field.COLLIN) {
            for (x in 0 until field.COLLIN) {
                possibles[y][x].clear()
                for (i in 1 until field.COLLIN + 1) {
                    if (field.getCellValue(x, y) == SudokuField.Companion.EMPTY) {
                        if (checkValidInput(x, y, i)) {
                            possibles[y][x].add(i)
                        }
                    }
                }
            }
        }
    }

    /**
     * game logic which must be implemented for every specific SudokuType
     *
     * @param x coordiante
     * @param y coordinate
     * @param val value which should be checked
     * @return if vale can be set at coordinates without violating the rules
     */
    protected abstract fun checkValidInput(x: Int, y: Int, `val`: Int): Boolean

    /**
     * checks if a value at a certain position can be found. Must be implemented for every specific SudokuType
     *
     * @param x coordinate
     * @param y coordinate
     * @param val: value which should be able to be found
     * @return if the value can be found
     */
    protected abstract fun checkForObvious(x: Int, y: Int, `val`: Int): Boolean


        /**
         * gives last removed cell
         *
         * @return SudoCell with coordinates and coorect solution
         */
        fun nextSolutionStep(): SudokuCell? = try {
            solutionSteps.pop()
        } catch (e: EmptyStackException) {
            null
        }

    private fun createRandom(): Int {
        val rand = Random()
        return rand.nextInt(field.COLLIN)
    }
}
