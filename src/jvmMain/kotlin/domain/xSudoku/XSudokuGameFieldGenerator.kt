package domain.xSudoku

import domain.entities.SudokuField
import domain.game.SudokuGameFieldGenerator

/**
 * a sudoku generator for a x sudoku extending an abstract sudoku.
 */
class XSudokuGameFieldGenerator(field: SudokuField) : SudokuGameFieldGenerator(field) {
    /**
     * checks if a value can be found
     *
     * @param x coordinate
     * @param y coordinate
     * @param val: value to check
     * @return if the falue can be found
     */
    override fun checkForObvious(x: Int, y: Int, `val`: Int): Boolean {
        return super.checkForObvious(x, y, `val`) || checkForObviousDiagonal(x, y, `val`)
    }

    /**
     *
     * @param x coordinate
     * @param y coordinate
     * @param val vaöue to check
     * @return boolean if value can be found only one time in the axis of the coordinates (returns false if the coordinates are not on any axis and checks for both if the value is on both axis (middle))
     */
    fun checkForObviousDiagonal(x: Int, y: Int, `val`: Int): Boolean {
        //checks if the coordiantes are on an axis, if not than false is returned
        var found = false
        val onLeftDiagonal = x == y
        val onRightDiagonal = x == java.lang.Math.abs(y - (field.COLLIN - 1))
        if (!onLeftDiagonal && !onRightDiagonal) return found
        //if the coordiantes are on an axis obvious solutions have to be checked
        if (onLeftDiagonal && onRightDiagonal) {
            //left diagonal check:
            var y1 = 0
            for (x1 in 0 until field.COLLIN) {
                if (possibles.get(y1).get(x1).contains(`val`)) {
                    found = if (!found) true else break
                    y1++
                }
            }
            //right diagonal check
            found = false
            y1 = field.COLLIN - 1
            for (x1 in 0 until field.COLLIN) {
                if (possibles.get(y1).get(x1).contains(`val`)) {
                    found = if (!found) true else return false
                    y1--
                }
            }
        }
        if (onLeftDiagonal) {
            var y1 = 0
            for (x1 in 0 until field.COLLIN) {
                if (possibles.get(y1).get(x1).contains(`val`)) {
                    found = if (!found) true else return false
                    y1++
                }
            }
        }
        if (onRightDiagonal) {
            var y1: Int = field.COLLIN - 1
            for (x1 in 0 until field.COLLIN) {
                if (possibles.get(y1).get(x1).contains(`val`)) {
                    found = if (!found) true else return false
                    y1--
                }
            }
        }
        return false
    }

    /**
     * Checks if input value in generelly possible by the x sudoku rules
     *
     * @param x coordinate
     * @param y coordinate
     * @param val: value to be checked
     * @return if input value in generelly possible by the x sudoku rules
     */
    override fun checkValidInput(x: Int, y: Int, `val`: Int): Boolean {
        return XSudokuSolver.checkValidInput(x, y, field, `val`)
    }
}
