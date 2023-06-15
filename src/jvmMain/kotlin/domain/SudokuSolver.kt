/**
 * Solver extending an AbstractSolver. Can be used to solve classical sudokus. Provides static methods containing the logical rules of a classical sudoku.
 * instanciates a solver with a SudokuField which shall be solved.
 *
 * @param field
*/
class SudokuSolver(field: SudokuField) : AbstractSudokuSolver(field) {
    /**
     * instance method checking if a value can be set on the instance field.
     *
     * @param x coorindate
     * @param y coordinate
     * @param val: value which should be checked
     * @return if value can be set without violating the classical rules
     */
    override fun checkValidInput(x: Int, y: Int, `val`: Int): Boolean {
        return checkValidInput(x, y, field, `val`)
    }

    companion object {
        /**
         * class method checking if a value can be set on a given field.
         *
         * @param x coorindate
         * @param y coordinate
         * @param field: field to check
         * @param val: value which should be checked
         * @return if value can be set without violating the classical rules
         */
        fun checkValidInput(x: Int, y: Int, field: SudokuField?, `val`: Int): Boolean {
            return if (field!!.compareValue(x, y, `val`)) true else checkBox(
                x,
                y,
                field,
                `val`
            ) && checkLine(y, field, `val`) && checkColumn(x, field, `val`)
        }

        /**
         * class method checking if a value can be set on a given field without violating the box rule.
         *
         * @param x coorindate
         * @param y coordinate
         * @param field: field to check
         * @param val: value which should be checked
         * @return if value can be set without violating the classical box rule
         */
        fun checkBox(x: Int, y: Int, field: SudokuField?, `val`: Int): Boolean {
            val xBox = x / field!!.BOXPERCOLLIN
            val yBox = y / field.BOXPERCOLLIN
            for (i_x in xBox * field.BOXPERCOLLIN until (xBox + 1) * field.BOXPERCOLLIN) {
                for (i_y in yBox * field.BOXPERCOLLIN until (yBox + 1) * field.BOXPERCOLLIN) {
                    if (field.compareValue(i_x, i_y, `val`)) return false
                }
            }
            return true
        }

        /**
         * class method checking if a value can be set on a given field without violating the line rule.
         *
         * @param x coorindate
         * @param y coordinate
         * @param field: field to check
         * @param val: value which should be checked
         * @return if value can be set without violating the classical line rule
         */
        fun checkLine(y: Int, field: SudokuField?, `val`: Int): Boolean {
            for (i_x in 0 until field!!.COLLIN) {
                if (field.compareValue(i_x, y, `val`)) return false
            }
            return true
        }

        /**
         * class method checking if a value can be set on a given field without violating the column rule.
         *
         * @param x coorindate
         * @param y coordinate
         * @param field: field to check
         * @param val: value which should be checked
         * @return if value can be set without violating the classical column rule
         */
        fun checkColumn(x: Int, field: SudokuField?, `val`: Int): Boolean {
            for (i_y in 0 until field!!.COLLIN) {
                if (field.compareValue(x, i_y, `val`)) return false
            }
            return true
        }
    }
}
