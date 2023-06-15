
class XSudokuGame extends AbstractSudokuGame {
	public static final GameFactory.GameMode GAMEMODE = GameFactory.GameMode.XSUDOKU;
	
	public XSudokuGame(GameFactory.Size size, GameFactory.Difficulty difficulty) {
		super(size, difficulty);
	}

	@Override
	protected AbstractSudokuSolver initiateSolver(SudokuField solutionField) {
		return new XSudokuSolver(solutionField);
	}

	@Override
	protected AbstractSudokuGameFieldGenerator initiateGenerator(SudokuField gameField) {
		return new XSudokuGameFieldGenerator(gameField);
	}
	
}
