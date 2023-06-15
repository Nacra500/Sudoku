import domain.DIFFICULTIES
import domain.SIZES

/**
 * the classical Version of a sudoku game extending the abstract SudokuGame
 */
internal class NormalSudokuGame(size: SIZES, difficulty: DIFFICULTIES) : AbstractSudokuGame(size, difficulty) {
    /**
     * instanciates the classical SudokuSolver
     * please find more information in the abstract sudoku game
     */
    override fun initiateSolver(solutionField: SudokuField): AbstractSudokuSolver {
        return SudokuSolver(solutionField)
    }

    /**
     * instanciates the classical SudokuGenerator
     * please find more information in the abstract sudoku game
     */
    override fun initiateGenerator(gameField: SudokuField): AbstractSudokuGameFieldGenerator {
        return SudokuGameFieldGenerator(gameField)
    }

    companion object {
        val GAMEMODE = GameFactory.GameMode.NORMAL
    }
}
