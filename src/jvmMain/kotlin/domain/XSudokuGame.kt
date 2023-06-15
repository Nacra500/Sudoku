/**
 *  the x sudoku Version of a sudoku game extending the abstract SudokuGame
 */
class XSudokuGame extends AbstractSudokuGame {
	public static final GameFactory.GameMode GAMEMODE = GameFactory.GameMode.XSUDOKU;
	
	public XSudokuGame(GameFactory.Size size, GameFactory.Difficulty difficulty) {
		super(size, difficulty);
	}

	/**
	 * instanciates the x SudokuSolver
	 * please find more information in the abstract sudoku game
	 */
	@Override
	protected AbstractSudokuSolver initiateSolver(SudokuField solutionField) {
		return new XSudokuSolver(solutionField);
	}

	/**
	 * instanciates the c Sudoku generator
	 * please find more information in the abstract sudoku game
	 */
	@Override
	protected AbstractSudokuGameFieldGenerator initiateGenerator(SudokuField gameField) {
		return new XSudokuGameFieldGenerator(gameField);
	}
	
}
