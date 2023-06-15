public class SudokuSolver extends AbstractSudokuSolver{
	
    public SudokuSolver(SudokuField field) {
		super(field);
	}

	boolean checkValidInput(int x, int y, int val) {
    	return checkValidInput(x, y, field, val);
    }

	public static boolean checkValidInput(int x, int y, SudokuField field, int val){
        if (field.compareValue(x, y, val)) return true;
        return checkBox(x,y,field, val) && checkLine(y, field, val) && checkColumn(x, field, val);
    }

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

	public static boolean checkLine(int y, SudokuField field, int val){
        for(int i_x = 0; i_x < field.COLLIN; i_x++){
            if (field.compareValue(i_x, y, val)) return false;
        }
        return true;
    }

	public static boolean checkColumn(int x, SudokuField field, int val){
        for(int i_y = 0; i_y < field.COLLIN; i_y++){
            if (field.compareValue(x, i_y, val)) return false;
        }
        return true;
    }
}
