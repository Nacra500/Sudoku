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
        return super.checkForObvious(x, y, `val`) && checkForObviousDiagonal(x, y, `val`)
    }

    //TODO: create doc
    private fun checkForObviousDiagonal(x: Int, y: Int, `val`: Int): Boolean {
        // TODO
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
        return XSudokuSolver.Companion.checkValidInput(x, y, field, `val`)
    }
}
