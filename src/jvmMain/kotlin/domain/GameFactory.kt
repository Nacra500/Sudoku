package domain
/**
 *
 * class which creates an instances of an Sudoku game from the given parameters.
 *
 */
object GameFactory {
    /**
     * method which creates an instance of the specified game
     *
     * @param difficulty
     * @param mode
     * @param size
     * @return SudokuGame of the specified type with the specified size and difficulty
     */
    fun getGame(difficulty: DIFFICULTIES, mode: GameModes?, size: SIZES): SudokuGame {
        return when (mode) {
            GameModes.XSUDOKU -> XSudokuGame(size, difficulty)
            else -> NormalSudokuGame(size, difficulty)
        }
    }

}
