import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

public class SudokuGameFieldGenerator extends AbstractSudokuGameFieldGenerator{

	public SudokuGameFieldGenerator(SudokuField field) {
		super(field);
	}
	
	protected boolean checkForObvious(int x, int y, int val) {
    	return checkForOneSolution(x, y) ||
    			checkForObviousColumn(x, val) ||
    			checkForObviousRow(y, val) ||
    			checkForObviousBox(x, y, val);
    }

    private boolean checkForObviousColumn(int x, int val) {
    	boolean found = false;
    	for (int y = 0; y < field.COLLIN; y++) {
            if (possibles[y][x].contains(val)) {
                if (!found) found = true;
                else return false;
            }
        }
    	return found;
    }

    private boolean checkForObviousRow(int y, int val) {
        // counts how often val is a possible Value in the row (if its exactly one, that
        // solution can be easily found by the user
    	boolean found = false;
        for (int x = 0; x < field.COLLIN; x++) {
            if (possibles[y][x].contains(val)) {
                if (!found) found = true;
                else return false;
            }
        }
        // if there is exactly one obvious soloution return the solution and its coodinates
        return found;
    }
    
    private boolean checkForObviousBox(int x, int y, int val) {
    	boolean found = false;
        int xb = x - (x % field.BOXPERCOLLIN);
        int yb = y - (y % field.BOXPERCOLLIN);
        for (int y2 = yb; y2 < yb + field.BOXPERCOLLIN; y2++) {
            for (int x2 = xb; x2 < xb + field.BOXPERCOLLIN; x2++) {
                if (possibles[y2][x2].contains(val)) {
                	 if (!found) found = true;
                     else return false;
                }

            }
        }
        return found;
    }

    private boolean checkForOneSolution(int x, int y) {
    	boolean found = false;
        for (int j = 1; j < field.COLLIN + 1; j++) {
            if (possibles[y][x].contains(j)) {
               if (!found) found = true;
               else return false;
            }
        }
        return found;
    }

	@Override
	protected boolean checkValidInput(int x, int y, int val) {
		return SudokuSolver.checkValidInput(x, y, this.field, val);
	}   
}
