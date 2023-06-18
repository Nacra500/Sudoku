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
		return super.checkForObvious(x, y, val) || checkForObviousDiagonal(x, y, val);
	}

	/**
	 * 
	 * @param x coordinate
	 * @param y coordinate
	 * @param val vaöue to check
	 * @return boolean if value can be found only one time in the axis of the coordinates (returns false if the coordinates are not on any axis and checks for both if the value is on both axis (middle))
	 */
	public boolean checkForObviousDiagonal(int x, int y, int val) {
		//checks if the coordiantes are on an axis, if not than false is returned
    	boolean found = false;
    	final boolean onLeftDiagonal = x == y;
    	final boolean onRightDiagonal = x == Math.abs(y - (field.COLLIN -1));
    	if (!onLeftDiagonal && !onRightDiagonal) return found;
    	//if the coordiantes are on an axis obvious solutions have to be checked
    	if (onLeftDiagonal && onRightDiagonal) {
    		//left diagonal check:
        	int y1 = 0;
        	for (int x1 = 0; x1 < field.COLLIN; x1++) {
                if (possibles[y1][x1].contains(val)) {
                    if (!found) found = true;
                    else break;
                    y1++;
                }
            }
        	//right diagonal check
        	found = false;
        	y1 = field.COLLIN-1;
        	for (int x1 = 0; x1 < field.COLLIN; x1++) {
                if (possibles[y1][x1].contains(val)) {
                    if (!found) found = true;
                    else return false;
                    y1--;
                }
            }
		}
    	
    	
    	if (onLeftDiagonal) {
        	int y1 = 0;
        	for (int x1 = 0; x1 < field.COLLIN; x1++) {
                if (possibles[y1][x1].contains(val)) {
                    if (!found) found = true;
                    else return false;
                    y1++;
                }
            }
		}
        
        if (onRightDiagonal) {
        	int y1 = field.COLLIN-1;
        	for (int x1 = 0; x1 < field.COLLIN; x1++) {
                if (possibles[y1][x1].contains(val)) {
                    if (!found) found = true;
                    else return false;
                    y1--;
                }
            }
		}

    	
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
