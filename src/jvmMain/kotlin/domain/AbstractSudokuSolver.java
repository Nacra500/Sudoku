import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A solver is a class which solves a given SudokuField.
 * The abstract Solver defines methods which every solver must have and implements a general backtracking algorithm.
 */
public abstract class AbstractSudokuSolver {
protected SudokuField field;

    /**
     * Instanciates a new Solver with a sudoku field
     *
     * @param field
     */
    public AbstractSudokuSolver(SudokuField field) {
		this.field = field;
	}

    /**
     * every solver needs a method which returns if a value can generally be set on a certain position
     *
     * @param x
     * @param y
     * @param val: value which should be checked
     * @return boolean if possible
     */
    abstract boolean checkValidInput(int x, int y, int val);

    /**
     * solves the Sudoku with a recursive backtracking algorithm
     *
     * @return if the field is solvable
     */
    public boolean solve(){
        List<Integer> entries;

        for (int i_y = 1; i_y < field.COLLIN; i_y++) { //for each line
                entries = IntStream.rangeClosed(1, field.COLLIN).boxed().collect(Collectors.toList()); //this function create a list of all the allowed input numbers
                Collections.shuffle(entries);

                for (int i_x = 0; i_x < field.COLLIN; i_x++) { // for each column
                    if (!field.compareValue(i_x, i_y, field.EMPTY)) continue;
                    for (int newVal : entries) { // for each general possible value
                        if (checkValidInput(i_x, i_y, newVal)) { //try if value can be set on the coorindates
                            field.setValue(i_x, i_y, newVal);
                            if (solve()) return true; // check if sudoku stays solvable and return true if so
                            else field.setValue(i_x, i_y, field.EMPTY); // else unset value and try next one of genereal possible values
                        }
                    }
                    if (field.compareValue(i_x, i_y, field.EMPTY)) return  false; // return false if no value leads to a solvable sudoku
                }
            }
        return true; //return true if all values are set
}
}
