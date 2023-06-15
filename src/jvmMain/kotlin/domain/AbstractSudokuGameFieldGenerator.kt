import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

/**
 * A Sudoku generator creates a uniquely solvable sudoku field with a specified number of missing cells.
 * The abstract generator privides some default implementations which are the same for every generator.
 */
public abstract class AbstractSudokuGameFieldGenerator {
	protected SudokuField field;
	protected Set<Integer>[][] possibles; // a two dimensional array of set containg all values which can possibly be set (and ot violating the rules) on the given coordinates
    private Stack<SudokuCell> solutionSteps; // a stack containing all removal steps
    private int removedCells = 0;
	
	public AbstractSudokuGameFieldGenerator(SudokuField field) {
		this.field = field;
		solutionSteps = new Stack<SudokuCell>();
		possibles = new Set[field.COLLIN][field.COLLIN];
        for (int i = 0; i < field.COLLIN; i++) {
            for (int j = 0; j < field.COLLIN; j++) {
                possibles[i][j] = new HashSet<Integer>();
            }
        }
	}

	/**
	 * algorthm which removes as many cells from the field as possible.
	 */
	public void createSolvable() {
    	createSolvable(field.COLLIN * field.COLLIN);
    }

	/**
	 * recursive algorthm which removes the specified number cells from the field.
	 *
	 * @param removeCount: defines how many cells should be removed
	 */
	public void createSolvable(int removeCount) {
		if (removeCount <= 0) return;
		int x = createRandom(); // defines a randomizez starting column to make sure that not only the first columns are removed
    	int y = createRandom(); // defines a randomizez starting row to make sure that not only the first columns are removed
    	int cellValue = field.EMPTY;
    	for (int i_x = 0; i_x < field.COLLIN; i_x++) { //for each column starting at the randomized startig column
    		for (int i_y = 0; i_y < field.COLLIN; i_y++) { //for each line starting at the randomized startig line
    			int posX = Math.abs(x + i_x - (field.COLLIN - 1)); //used for the randomized shift
				int posY = Math.abs(y + i_y - (field.COLLIN - 1)); //used for the randomized shift
    			if (field.getCellValue(posX, posY) != SudokuField.EMPTY) { // if the cell value at the position is not empty
        			cellValue = field.getCellValue(posX, posY);
    				field.removeNumber(posX, posY); // clear cell
    		    	analyzeField(); // find all possibles
    		    	if (checkForObvious(posX, posY, cellValue)) { // look if the removed cell can be found.
						// if yes ...
    		    		removeCount--;
    		    		this.removedCells++;
    		    		createSolvable(removeCount); // try to remove the next number
    		    		solutionSteps.add(new SudokuCell(posX, posY, cellValue));
    		    		return; // and stop iterating
    		    	} else {
    		    		field.setValue(posX, posY, cellValue); // refill cleared cell and try next one
    		    	}
    			}
    		}
    	}
    }

	/**
	 * helper function which provides all possibel values for all fields
	 */
    private void analyzeField() {
        for (int y = 0; y < field.COLLIN; y++) {
            for (int x = 0; x < field.COLLIN; x++) {
                possibles[y][x].clear();
                for (int i = 1; i < field.COLLIN + 1; i++) {
                    if (field.getCellValue(x, y) == SudokuField.EMPTY) {
                        if (checkValidInput(x, y, i)) {
                            possibles[y][x].add(i);
                        }
                    }
                }
            }
        }
    }

	/**
	 * game logic which must be implemented for every specific SudokuType
	 *
	 * @param x coordiante
	 * @param y coordinate
	 * @param val value which should be checked
	 * @return if vale can be set at coordinates without violating the rules
	 */
    protected abstract boolean checkValidInput(int x, int y, int val);

	/**
	 * checks if a value at a certain position can be found. Must be implemented for every specific SudokuType
	 *
	 * @param x coordinate
	 * @param y coordinate
	 * @param val: value which should be able to be found
	 * @return if the value can be found
	 */
    protected abstract boolean checkForObvious(int x, int y, int val);
    
    public int getNumberOfRemovedCells() {
    	return removedCells;
    }

	/**
	 * gives last removed cell
	 *
	 * @return SudoCell with coordinates and coorect solution
	 */
	public SudokuCell getNextSolutionStep() {
    	try {
			return solutionSteps.pop();
		} catch (EmptyStackException e) {
			return null;
		}
    }
    
    private Integer createRandom() {
        Random rand = new Random();
        int n = rand.nextInt(field.COLLIN);
        return n;
    }
	
}
