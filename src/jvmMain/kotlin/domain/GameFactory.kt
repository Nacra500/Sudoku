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
    fun getGame(difficulty: Difficulty, mode: GameMode?, size: Size): SudokuGame {
        return when (mode) {
            GameMode.XSUDOKU -> XSudokuGame(size, difficulty)
            else -> NormalSudokuGame(size, difficulty)
        }
    }

    /**
     *
     * specifies how many cells (in percent) are removed from the full sudoku.
     *
     */
    enum class Difficulty(val percentShown: Int) {
        EASY(20),
        MEDIUM(35),
        HARD(50)
    }

    /**
     *
     * Specifies the sudoku variant
     *
     */
    enum class GameMode {
        NORMAL,
        EVENODD,
        XSUDOKU
    }

    /**
     *
     * specifies the size of the sudoku, thus how many lines and columns it has.
     *
     */
    enum class Size(val COLLLIN: Int) {
        MEDIUM(9),
        LARGE(16)
    }
}
