import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class XSudokuGenerator extends SudokuGenerator{

    public XSudokuGenerator(int boxPerColLin) {
        super(boxPerColLin);
    }

    @Override
    private booleancheckValidInput(int x, int y, Integer val) {
        if (field[y][x].equals(val)) return true
        return (checkBox(x,y,val)
                && checkLine( y,val)
                && checkColumn(x, val)
                && checkDiagonal(x, y, val));
    }

    private boolean checkDiagonal(int x, int y, Integer val) {
        for (int i_x = 0; i_x < field.size(); i_x++) {
            if (field[i_x][i_x].equals(val)) return false;
            for (int i_y = field.size()-1; i_y >= 0; i_y--) {
                if (field[i_y][i_x].equals(val)) return false;
            }
        }
    }

}
