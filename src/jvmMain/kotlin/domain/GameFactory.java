
public class GameFactory {

	public enum Difficulty {
		EASY(20),
		MEDIUM(35),
		HARD(50);

		public final int percentShown;
		
		Difficulty(int percentShown) {
			this.percentShown = percentShown;
		}
	}
	
	public enum GameMode {
		NORMAL,
		EVENODD,
		XSUDOKU
	}
	
	public enum Size {
		SMALL(4),
		MEDIUM(9),
		LARGE(16);

		public final int COLLLIN;
		
		Size(int colLin) {
			this.COLLLIN = colLin;
		}
	}
	
	public static SudokuGame getGame(Difficulty difficulty, GameMode mode, Size size) {
		switch (mode) {
		case XSUDOKU:
			return new XSudokuGame(size, difficulty);
		default:
			return new NormalSudokuGame(size, difficulty);
		}
	}
	
}
