
public class XSudokuGameFieldGenerator extends SudokuGameFieldGenerator {

	public XSudokuGameFieldGenerator(SudokuField field) {
		super(field);
	}

	@Override
	protected boolean checkForObvious(int x, int y, int val) {
		return super.checkForObvious(x, y, val) && checkForObviousDiagonal(x, y, val);
	}

	private boolean checkForObviousDiagonal(int x, int y, int val) {
		// TODO
		return false;
	}
	
	@Override
	protected boolean checkValidInput(int x, int y, int val) {
		return XSudokuSolver.checkValidInput(x, y, this.field, val);
	}
}
