import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestXSudokuSolver {


	SudokuField field;

	@BeforeEach
	void setUp() throws Exception {
		int empty = SudokuField.EMPTY;
		int[][] arrField = {
				{5,		3,		4,		6,		7,		empty,	9,		1,		2},
				{6, 	empty,	2,		1,		9,		5,		3,		4,		8},
				{1, 	9,		8,		3,		4,		2,		empty,	6,		7},
				{8,		5,		9,		empty,	6,		7,		4,		2,		3},
				{4,		2,		6,		8,		5,		3,		7,		empty,	1},
				{7,		1,		empty,	9,		2,		4,		8,		5,		6},
				{empty,	6,		1,		5,		3,		7,		2,		8,		4},
				{2,		8,		7,		4,		empty,	9,		6,		3,		5},
				{3,		4,		5,		2,		8,		6,		1,		empty,	empty}
				};
		
		field = new SudokuField(arrField);
	}
	
	@Test
	void testCheckDiagonal() {
		assertTrue(XSudokuSolver.checkDiagonal(6, 2, field, 6));
		assertFalse(XSudokuSolver.checkDiagonal(6, 2, field, 7));
	}

}
