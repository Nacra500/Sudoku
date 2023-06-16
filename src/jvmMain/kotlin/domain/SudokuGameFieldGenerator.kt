package domain

/**
 * a sudoku generator for a classical sudoku extending an abstract sudoku.
 */
open class SudokuGameFieldGenerator(field: SudokuField) : AbstractSudokuGameFieldGenerator(field) {
    /**
     * checks if a value can be found
     *
     * @param x coordinate
     * @param y coordinate
     * @param val: value to check
     * @return if the falue can be found
     */
    override fun checkForObvious(x: Int, y: Int, `val`: Int): Boolean {
        return possibles[y][x].size == 1 ||
                checkForObviousColumn(x, `val`) ||
                checkForObviousRow(y, `val`) ||
                checkForObviousBox(x, y, `val`)
    }

    /**
     * checks if value can be found only one time in column of possibles
     *
     * @param x: coordinate of cooumn
     * @param val: value to be checked
     * @return boolean if value can be found only one time in column of possibles
     */
    private fun checkForObviousColumn(x: Int, `val`: Int): Boolean {
        var found = false
        for (y in 0 until field.COLLIN) {
            if (possibles[y][x].contains(`val`)) {
                found = if (!found) true else return false
            }
        }
        return found
    }

    /**
     * checks if value can be found only one time in row of possibles
     *
     * @param y: coordinate of row
     * @param val: value to be checked
     * @return boolean if value can be found only one time in row of possibles
     */
    private fun checkForObviousRow(y: Int, `val`: Int): Boolean {
        // counts how often val is a possible Value in the row (if its exactly one, that
        // solution can be easily found by the user
        var found = false
        for (x in 0 until field.COLLIN) {
            if (possibles[y][x].contains(`val`)) {
                found = if (!found) true else return false
            }
        }
        // if there is exactly one obvious soloution return the solution and its coodinates
        return found
    }

    /**
     * checks if value can be found only one time in box of possibles
     *
     * @param x: coordinate of colum
     * @param y: coordnate of row
     * @param val: value to be checked
     * @return boolean if value can be found only one time in box of possibles
     */
    private fun checkForObviousBox(x: Int, y: Int, `val`: Int): Boolean {
        var found = false
        val xb = x - x % field.BOXPERCOLLIN
        val yb = y - y % field.BOXPERCOLLIN
        for (y2 in yb until yb + field.BOXPERCOLLIN) {
            for (x2 in xb until xb + field.BOXPERCOLLIN) {
                if (possibles[y2][x2].contains(`val`)) {
                    found = if (!found) true else return false
                }
            }
        }
        return found
    }

    /**
     * Checks if input value in generelly possible by the classical rules
     *
     * @param x coordinate
     * @param y coordinate
     * @param val: value to be checked
     * @return if input value in generelly possible by the classical rules
     */
    override fun checkValidInput(x: Int, y: Int, `val`: Int): Boolean {
        return SudokuSolver.Companion.checkValidInput(
            x = x,
            y = y,
            field = field,
            `val` = `val`
        ) //this method equaly the already existing one of the solver which can be used
    }
}
