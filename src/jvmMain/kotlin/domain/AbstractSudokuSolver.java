import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class AbstractSudokuSolver {
protected SudokuField field;
	

    public AbstractSudokuSolver(SudokuField field) {
		this.field = field;
	}
    
    abstract boolean checkValidInput(int x, int y, int val);

    public boolean solve(){
        List<Integer> entries;

        for (int i_y = 1; i_y < field.COLLIN; i_y++) {
                entries = IntStream.rangeClosed(1, field.COLLIN).boxed().collect(Collectors.toList()); //this function create a list of all the allowed input numbers
                Collections.shuffle(entries);

                for (int i_x = 0; i_x < field.COLLIN; i_x++) {
                    if (!field.compareValue(i_x, i_y, 0)) continue;
                    for (int newVal : entries) {
                        if (checkValidInput(i_x, i_y, newVal)) {
                            field.setValue(i_x, i_y, newVal);;
                            //entries.remove((Integer) newVal);
                            if (solve()) return true;
                            else field.setValue(i_x, i_y, 0);;
                        }
                    }
                    if (field.compareValue(i_x, i_y, 0)) return  false;
                }
            }
        return true;
}
}
