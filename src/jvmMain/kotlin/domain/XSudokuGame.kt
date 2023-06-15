import domain.DIFFICULTIES
import domain.SIZES

/**
 * the x sudoku Version of a sudoku game extending the abstract SudokuGame
 */
internal class XSudokuGame(size: SIZES, difficulty: DIFFICULTIES) : AbstractSudokuGame(size, difficulty) {
    /**
     * instanciates the x SudokuSolver
     * please find more information in the abstract sudoku game
     */
    override fun initiateSolver(solutionField: SudokuField): AbstractSudokuSolver {
        return XSudokuSolver(solutionField)
    }

    /**
     * instanciates the c Sudoku generator
     * please find more information in the abstract sudoku game
     */
    override fun initiateGenerator(gameField: SudokuField): AbstractSudokuGameFieldGenerator {
        return XSudokuGameFieldGenerator(gameField)
    }

    companion object {
        val GAMEMODE = GameFactory.GameMode.XSUDOKU
    }
}
