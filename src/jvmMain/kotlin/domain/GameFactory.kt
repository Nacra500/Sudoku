import domain.DIFFICULTIES
import domain.SIZES

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
    fun getGame(difficulty: DIFFICULTIES, mode: GameMode?, size: SIZES): SudokuGame {
        return when (mode) {
            GameMode.XSUDOKU -> XSudokuGame(size, difficulty)
            else -> NormalSudokuGame(size, difficulty)
        }
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

}
