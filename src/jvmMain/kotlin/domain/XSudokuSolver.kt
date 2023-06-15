/**
 * Solver extending an AbstractSolver. Can be used to solve x sudokus. Provides static methods containing the logical rules of a x sudoku.
 */
public class XSudokuSolver extends AbstractSudokuSolver {

	public XSudokuSolver(SudokuField field) {
		super(field);
	}

	/**
	 * instance method checking if a value can be set on the instance field.
	 *
	 * @param x coorindate
	 * @param y coordinate
	 * @param val: value which should be checked
	 * @return if value can be set without violating the x sudoku rules
	 */
	@Override
	boolean checkValidInput(int x, int y, int val) {
		return checkValidInput(x, y, super.field, val);
	}

	/**
	 * class method checking if a value can be set on a given field.
	 *
	 * @param x coorindate
	 * @param y coordinate
	 * @param field: field to check
	 * @param val: value which should be checked
	 * @return if value can be set without violating the x sudoku rules
	 */
	public static boolean checkValidInput(int x, int y, SudokuField field, int val){
        if (field.compareValue(x, y, val)) return true;
        return SudokuSolver.checkBox(x,y,field, val) 
        		&& SudokuSolver.checkLine(y, field, val) 
        		&& SudokuSolver.checkColumn(x, field, val)
        		&& checkDiagonal(x, y, field, val);
    }

	/**
	 * class method checking if a value can be set on a given field without violating the x sudoku diagonal rule.
	 *
	 * @param x coorindate
	 * @param y coordinate
	 * @param field: field to check
	 * @param val: value which should be checked
	 * @return if value can be set without violating the x sudoku diagonal rule
	 */
    public static boolean checkDiagonal(int x, int y, SudokuField field, int val) {
    	final boolean onLeftDiagonal = x == y;
    	final boolean onRightDiagonal = x == Math.abs(y - (field.COLLIN -1));
    	if (!onLeftDiagonal && !onRightDiagonal) return true;
        for (int i_x = 0; i_x < field.COLLIN; i_x++) {
            if (field.compareValue(i_x, i_x, val) && onLeftDiagonal) return false;
            if (field.compareValue(i_x, field.COLLIN -1 - i_x, val) && onRightDiagonal) return false;
        }
        return true;
    }
}
