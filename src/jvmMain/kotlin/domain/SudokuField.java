import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SudokuField {
	
	public static final int EMPTY = 0;
	
	public final int COLLIN;
	public final int BOXPERCOLLIN;
	public final int TOTALNUMBEROFCELLS;
	
	private int[][] field;

	public SudokuField(int[][] field) {
		final int y_size = field.length;
		final int x_size = field[0].length;
		if (Math.sqrt(y_size) % 1 != 0 ||  
			Math.sqrt(x_size) % 1 != 0) throw new IllegalFieldSizeException("The field size has to be a number to the power of two.");
		if (y_size != x_size) throw new IllegalFieldSizeException("The field x_size and y_size has to be the same.");
		COLLIN = x_size;
		BOXPERCOLLIN = (int) Math.sqrt(COLLIN);
		TOTALNUMBEROFCELLS = COLLIN * COLLIN;
		this.field = field;
	}
	
	public SudokuField(int colLin) {
		if (Math.sqrt(colLin) % 1 != 0) throw new IllegalFieldSizeException("The field size has to be a number to the power of two.");
		COLLIN = colLin;
		BOXPERCOLLIN = (int) Math.sqrt(COLLIN);
		TOTALNUMBEROFCELLS = COLLIN * COLLIN;
		field = new int[colLin][colLin];
		for (int[] line: field){
	            Arrays.fill(line, EMPTY);
	        }
	}
	
	public SudokuField(SudokuField field) {
		int[][] newField = new int[field.COLLIN][field.COLLIN];
		for (int x = 0; x < field.COLLIN; x++) {
			for (int y = 0; y < field.COLLIN; y++) {
				newField[y][x] = field.getCellValue(x, y);
			}
		}
		COLLIN = field.COLLIN;
		BOXPERCOLLIN = field.BOXPERCOLLIN;
		TOTALNUMBEROFCELLS = field.TOTALNUMBEROFCELLS;
		this.field = newField;
	}
	
	
	public int getCellValue(int x, int y) {
		if (x >= COLLIN || x < 0 ||
				y >= COLLIN || y < 0) throw new IllegalArgumentException("x and y must be whithin the boundaries of the field of " + COLLIN + "x" + COLLIN + "." );
		return this.field[y][x];
	}
	
	public void setValue(int x, int y, int val) {
		if (x >= COLLIN || x < 0 ||
				y >= COLLIN || y < 0) throw new IllegalArgumentException("x and y must be whithin the boundaries of the field of " + COLLIN + "x" + COLLIN + "." );
		field[y][x] = val;
	}
	
	public boolean compareValue(int x, int y, int val) {
		if (x >= COLLIN || x < 0 ||
				y >= COLLIN || y < 0) throw new IllegalArgumentException("x and y must be whithin the boundaries of the field of " + COLLIN + "x" + COLLIN + "." );
		return field[y][x] == val;
	}
	
	public static List<SudokuCell> compareFields(SudokuField field1, SudokuField field2) {
		if (field1.COLLIN != field2.COLLIN) throw new IllegalFieldSizeException("Both fields must have the same size to be compared.");
		List<SudokuCell> res = new ArrayList<SudokuCell>();
		int colLin = field1.COLLIN;
		for (int y = 0; y < colLin; y++) {
            for (int x = 0; x < colLin; x++) {
            	int val = field2.getCellValue(x, y);
                if (field1.getCellValue(x, y) != val && field1.getCellValue(x, y) != EMPTY) {                	
                   res.add(new SudokuCell(x, y, val));
                }
            }
        }
		return res;
	}
	
    public void displayInConsole() {
        for (int l = 0; l < COLLIN; l++) {
            int[] line = field[l];
            for (int col = 0; col < COLLIN; col++) {
            	int num = line[col];
            	if (num != EMPTY) System.out.print(num + " ");
            	else System.out.print("  ");
                if ((col + 1) % BOXPERCOLLIN == 0)
                    System.out.print("|");
            }
            System.out.print("\n");
            if ((l + 1) % BOXPERCOLLIN == 0) {
                for (int i = 0; i < COLLIN; i++) {
                    System.out.print("__");
                }
                System.out.print("\n");
            }
        }
    }
    
    public void removeNumber(int x, int y) {
        field[y][x] = EMPTY;
    }
}
