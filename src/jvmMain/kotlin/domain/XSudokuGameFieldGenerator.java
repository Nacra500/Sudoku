/**
 * a sudoku generator for a x sudoku extending an abstract sudoku.
 */
public class XSudokuGameFieldGenerator extends SudokuGameFieldGenerator {

	public XSudokuGameFieldGenerator(SudokuField field) {
		super(field);
	}

	/**
	 * checks if a value can be found
	 *
	 * @param x coordinate
	 * @param y coordinate
	 * @param val: value to check
	 * @return if the falue can be found
	 */
	@Override
	protected boolean checkForObvious(int x, int y, int val) {
		return super.checkForObvious(x, y, val) && checkForObviousDiagonal(x, y, val);
	}

	//TODO: create doc
	private boolean checkForObviousDiagonal(int x, int y, int val) {
		// TODO
		return false;
	}

	/**
	 * Checks if input value in generelly possible by the x sudoku rules
	 *
	 * @param x coordinate
	 * @param y coordinate
	 * @param val: value to be checked
	 * @return if input value in generelly possible by the x sudoku rules
	 */
	@Override
	protected boolean checkValidInput(int x, int y, int val) {
		return XSudokuSolver.checkValidInput(x, y, this.field, val);
	}
}
