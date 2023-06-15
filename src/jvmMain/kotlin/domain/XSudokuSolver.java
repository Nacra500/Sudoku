
public class XSudokuSolver extends AbstractSudokuSolver {

	public XSudokuSolver(SudokuField field) {
		super(field);
	}

	@Override
	boolean checkValidInput(int x, int y, int val) {
		return checkValidInput(x, y, super.field, val);
	}
	 
	
	public static boolean checkValidInput(int x, int y, SudokuField field, int val){
        if (field.compareValue(x, y, val)) return true;
        return SudokuSolver.checkBox(x,y,field, val) 
        		&& SudokuSolver.checkLine(y, field, val) 
        		&& SudokuSolver.checkColumn(x, field, val)
        		&& checkDiagonal(x, y, field, val);
    }
	
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
