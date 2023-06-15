import java.util.List;

public interface SudokuGame {

    public SudokuField generate();
    public SudokuField getSolution();
    public boolean checkValidInput(int x, int y, Integer val, SudokuField field);
    public List<SudokuCell> showMistakes(SudokuField field);
    public SudokuCell getHint(SudokuField field);
	
}
