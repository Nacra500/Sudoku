import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class NormalSudokuGame extends AbstractSudokuGame{
	public static final GameFactory.GameMode GAMEMODE = GameFactory.GameMode.NORMAL;
	
	public NormalSudokuGame(GameFactory.Size size, GameFactory.Difficulty difficulty) {
		super(size, difficulty);
	}

	@Override
	protected AbstractSudokuSolver initiateSolver(SudokuField solutionField) {
		return new SudokuSolver(solutionField);
	}

	@Override
	protected AbstractSudokuGameFieldGenerator initiateGenerator(SudokuField gameField) {
		return new SudokuGameFieldGenerator(gameField);
	}
}
