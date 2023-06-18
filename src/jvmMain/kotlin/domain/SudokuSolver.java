/**
 * Solver extending an AbstractSolver. Can be used to solve classical sudokus. Provides static methods containing the logical rules of a classical sudoku.
 */
public class SudokuSolver extends AbstractSudokuSolver{

    /**
     * instanciates a solver with a SudokuField which shall be solved.
     *
     * @param field
     */
    public SudokuSolver(SudokuField field) {
		super(field);
	}

    /**
     * instance method checking if a value can be set on the instance field.
     *
     * @param x coorindate
     * @param y coordinate
     * @param val: value which should be checked
     * @return if value can be set without violating the classical rules
     */
	protected boolean checkValidInput(int x, int y, int val) {
    	return checkValidInput(x, y, field, val);
    }

    /**
     * class method checking if a value can be set on a given field.
     *
     * @param x coorindate
     * @param y coordinate
     * @param field: field to check
     * @param val: value which should be checked
     * @return if value can be set without violating the classical rules
     */
	public static boolean checkValidInput(int x, int y, SudokuField field, int val){
        if (field.compareValue(x, y, val)) return true;
        return checkBox(x,y,field, val) && checkLine(y, field, val) && checkColumn(x, field, val);
    }

    /**
     * class method checking if a value can be set on a given field without violating the box rule.
     *
     * @param x coorindate
     * @param y coordinate
     * @param field: field to check
     * @param val: value which should be checked
     * @return if value can be set without violating the classical box rule
     */
	public static boolean checkBox(int x, int y, SudokuField field, int val){
        final int xBox = (x/field.BOXPERCOLLIN) ;
        final int yBox = (y/field.BOXPERCOLLIN) ;
        for (int i_x = xBox * field.BOXPERCOLLIN; i_x < (xBox+1) * field.BOXPERCOLLIN; i_x++){
            for (int i_y = yBox * field.BOXPERCOLLIN; i_y < (yBox+1) * field.BOXPERCOLLIN; i_y++){
                if (field.compareValue(i_x, i_y, val)) return false;
            }
        }
        return true;
    }

    /**
     * class method checking if a value can be set on a given field without violating the line rule.
     *
     * @param x coorindate
     * @param y coordinate
     * @param field: field to check
     * @param val: value which should be checked
     * @return if value can be set without violating the classical line rule
     */
	public static boolean checkLine(int y, SudokuField field, int val){
        for(int i_x = 0; i_x < field.COLLIN; i_x++){
            if (field.compareValue(i_x, y, val)) return false;
        }
        return true;
    }

    /**
     * class method checking if a value can be set on a given field without violating the column rule.
     *
     * @param x coorindate
     * @param y coordinate
     * @param field: field to check
     * @param val: value which should be checked
     * @return if value can be set without violating the classical column rule
     */
	public static boolean checkColumn(int x, SudokuField field, int val){
        for(int i_y = 0; i_y < field.COLLIN; i_y++){
            if (field.compareValue(x, i_y, val)) return false;
        }
        return true;
    }
}
