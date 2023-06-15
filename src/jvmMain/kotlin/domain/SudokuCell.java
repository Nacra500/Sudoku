/**
 * class which can be used as a return value. Describes all important attributes a Cell has (coordinates and value)
 */
public class SudokuCell {

	public int x;
	public int y;
	public int val;
	
	
	public SudokuCell(int x, int y, int val) {
		this.x = x;
		this.y = y;
		this.val = val;
	}
}
