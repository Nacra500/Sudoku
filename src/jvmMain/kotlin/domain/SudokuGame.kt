package domain

/**
 *
 * defines the methods every sudoku game should have and servers as the connection between domain and view layer.
 *
 */
interface SudokuGame {
    /**
     * creates an SudokuGame. For this it creates the Fullfield and removes the specified number of Cells from it
     *
     * @return generated SudokuField with missing cells
     */
    fun generate(): SudokuField

    /**
     * gives the generated Fullfield.
     * @return empty SudokuField if nothing was generated priorly. Returns FullField otherwise
     */
    val solution: SudokuField

    /**
     * returns if value can be set on given coordinates.
     *
     * @param x: x coordinate of cell
     * @param y: y coordinate of cell
     * @param val: value which should be checked
     * @param field: Sudokufield in which the value should be checked
     * @return if value can be set according the game rules. (Does not check in solution.)
     */
    fun checkValidInput(x: Int, y: Int, `val`: Int, field: SudokuField?): Boolean

    /**
     * return a list of mistakes. Checks against in instance property which is holding the solution.
     *
     * @param SudokuField (gamefield) which should be checked.
     * @return list of Sudoku cells containing the coordinates of the mistake and the correct solution
     */
    fun showMistakes(field: SudokuField): List<SudokuCell>

    /**
     * gives a hint. The hint is a mistake if there are mistakes otherwise it gives the next step of the solution.
     *
     * @param takes sudokuField (gameField) for which the hint should be generated
     * @return SudokuField with the coordinates and the value of the hint.
     */
    fun getHint(field: SudokuField): SudokuCell?
}
