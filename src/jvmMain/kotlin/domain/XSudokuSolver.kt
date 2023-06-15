/**
 * Solver extending an AbstractSolver. Can be used to solve x sudokus. Provides static methods containing the logical rules of a x sudoku.
 */
class XSudokuSolver(field: SudokuField) : AbstractSudokuSolver(field) {
    /**
     * instance method checking if a value can be set on the instance field.
     *
     * @param x coorindate
     * @param y coordinate
     * @param val: value which should be checked
     * @return if value can be set without violating the x sudoku rules
     */
    override fun checkValidInput(x: Int, y: Int, `val`: Int): Boolean {
        return checkValidInput(x, y, super.field, `val`)
    }

    companion object {
        /**
         * class method checking if a value can be set on a given field.
         *
         * @param x coorindate
         * @param y coordinate
         * @param field: field to check
         * @param val: value which should be checked
         * @return if value can be set without violating the x sudoku rules
         */
        fun checkValidInput(x: Int, y: Int, field: SudokuField?, `val`: Int): Boolean {
            return if (field!!.compareValue(x, y, `val`)) true else (SudokuSolver.Companion.checkBox(x, y, field, `val`)
                    && SudokuSolver.Companion.checkLine(y, field, `val`)
                    && SudokuSolver.Companion.checkColumn(x, field, `val`)
                    && checkDiagonal(x, y, field, `val`))
        }

        /**
         * class method checking if a value can be set on a given field without violating the x sudoku diagonal rule.
         *
         * @param x coorindate
         * @param y coordinate
         * @param field: field to check
         * @param val: value which should be checked
         * @return if value can be set without violating the x sudoku diagonal rule
         */
        fun checkDiagonal(x: Int, y: Int, field: SudokuField?, `val`: Int): Boolean {
            val onLeftDiagonal = x == y
            val onRightDiagonal = x == Math.abs(y - (field!!.COLLIN - 1))
            if (!onLeftDiagonal && !onRightDiagonal) return true
            for (i_x in 0 until field.COLLIN) {
                if (field.compareValue(i_x, i_x, `val`) && onLeftDiagonal) return false
                if (field.compareValue(i_x, field.COLLIN - 1 - i_x, `val`) && onRightDiagonal) return false
            }
            return true
        }
    }
}
