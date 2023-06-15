import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

public abstract class AbstractSudokuGameFieldGenerator {


	protected SudokuField field;
	protected Set<Integer>[][] possibles;
    private Stack<SudokuCell> solutionSteps;
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
	
	public void createSolvable() {
    	createSolvable(field.COLLIN * field.COLLIN);
    }
    
    public void createSolvable(int removeCount) {
    	int x = createRandom();
    	int y = createRandom();
    	int cellValue = field.EMPTY;
    	for (int i_x = 0; i_x < field.COLLIN; i_x++) {
    		for (int i_y = 0; i_y < field.COLLIN; i_y++) {
    			int posX = Math.abs(x + i_x - (field.COLLIN - 1));
				int posY = Math.abs(y + i_y - (field.COLLIN - 1));
    			if (field.getCellValue(posX, posY) != SudokuField.EMPTY) {
        			cellValue = field.getCellValue(posX, posY);
    				field.removeNumber(posX, posY);
    		    	analyzeField();
    		    	if (checkForObvious(posX, posY, cellValue)) {
    		    		removeCount--;
    		    		this.removedCells++;
    		    		if (removeCount <= 0) return;
    		    		createSolvable(removeCount);
    		    		solutionSteps.add(new SudokuCell(posX, posY, cellValue));
    		    		return;
    		    	} else {
    		    		field.setValue(posX, posY, cellValue);;
    		    	}
    			}
    		}
    	}
    }
	
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

    protected abstract boolean checkValidInput(int x, int y, int val);
    
    protected abstract boolean checkForObvious(int x, int y, int val);
    
    public int getNumberOfRemovedCells() {
    	return removedCells;
    }
    
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
