/**
 *
 * class which creates an instances of an Sudoku game from the given parameters.
 *
 */
public class GameFactory {

	/**
	 *
	 * specifies how many cells (in percent) are removed from the full sudoku.
	 *
	 */
	public enum Difficulty {
		EASY(20),
		MEDIUM(35),
		HARD(50);

		public final int percentShown;

		Difficulty(int percentShown) {
			this.percentShown = percentShown;
		}
	}

	/**
	 *
	 * Specifies the sudoku variant
	 *
	 */
	public enum GameMode {
		NORMAL,
		EVENODD,
		XSUDOKU
	}

	/**
	 *
	 * specifies the size of the sudoku, thus how many lines and columns it has.
	 *
	 */
	public enum Size {
		MEDIUM(9),
		LARGE(16);

		public final int COLLLIN;

		Size(int colLin) {
			this.COLLLIN = colLin;
		}
	}

	/**
	 * method which creates an instance of the specified game
	 *
	 * @param difficulty
	 * @param mode
	 * @param size
	 * @return SudokuGame of the specified type with the specified size and difficulty
	 */
	public static SudokuGame getGame(Difficulty difficulty, GameMode mode, Size size) {
		switch (mode) {
			case XSUDOKU:
				return new XSudokuGame(size, difficulty);
			default:
				return new NormalSudokuGame(size, difficulty);
		}
	}

}
