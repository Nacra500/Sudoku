/**
 *
 * is thrown when the given arguments do not belong to a sudoku field.
 *
 */
public class IllegalFieldSizeException extends IllegalArgumentException {

	public IllegalFieldSizeException(String text) {
		super(text);
	}

}
