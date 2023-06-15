import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

/**
 * a sudoku generator for a classical sudoku extending an abstract sudoku.
 */
public class SudokuGameFieldGenerator extends AbstractSudokuGameFieldGenerator{

	public SudokuGameFieldGenerator(SudokuField field) {
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
	protected boolean checkForObvious(int x, int y, int val) {
    	return possibles[y][x].size() == 1 ||
    			checkForObviousColumn(x, val) ||
    			checkForObviousRow(y, val) ||
    			checkForObviousBox(x, y, val);
    }

    /**
     * checks if value can be found only one time in column of possibles
     *
     * @param x: coordinate of cooumn
     * @param val: value to be checked
     * @return boolean if value can be found only one time in column of possibles
     */
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

    /**
     * checks if value can be found only one time in row of possibles
     *
     * @param y: coordinate of row
     * @param val: value to be checked
     * @return boolean if value can be found only one time in row of possibles
     */
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

    /**
     * checks if value can be found only one time in box of possibles
     *
     * @param x: coordinate of colum
     * @param y: coordnate of row
     * @param val: value to be checked
     * @return boolean if value can be found only one time in box of possibles
     */
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

    /**
     * Checks if input value in generelly possible by the classical rules
     *
     * @param x coordinate
     * @param y coordinate
     * @param val: value to be checked
     * @return if input value in generelly possible by the classical rules
     */
	@Override
	protected boolean checkValidInput(int x, int y, int val) {
		return SudokuSolver.checkValidInput(x, y, this.field, val); //this method equaly the already existing one of the solver which can be used
	}   
}
