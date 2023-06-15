import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *  the classical Version of a sudoku game extending the abstract SudokuGame
 */
class NormalSudokuGame extends AbstractSudokuGame{
	public static final GameFactory.GameMode GAMEMODE = GameFactory.GameMode.NORMAL;

	public NormalSudokuGame(GameFactory.Size size, GameFactory.Difficulty difficulty) {
		super(size, difficulty);
	}

	/**
	 * instanciates the classical SudokuSolver
	 * please find more information in the abstract sudoku game
	 */
	@Override
	protected AbstractSudokuSolver initiateSolver(SudokuField solutionField) {
		return new SudokuSolver(solutionField);
	}

	/**
	 * instanciates the classical SudokuGenerator
	 * please find more information in the abstract sudoku game
	 */
	@Override
	protected AbstractSudokuGameFieldGenerator initiateGenerator(SudokuField gameField) {
		return new SudokuGameFieldGenerator(gameField);
	}
}
