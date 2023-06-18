import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class TestSudokuSolver {
	
	SudokuField field;
	SudokuSolver solver;

	@BeforeEach
	void setUp() throws Exception {
		int empty = SudokuField.EMPTY;
		int[][] arrField = {
				{empty,	empty,	empty,	empty,	empty,	empty,	empty,	empty,	empty},
				{empty, 1,		2,		empty,	3,		4,		5,		6,		7},
				{empty, 3,		4,		5,		empty,	6,		1,		8,		2},
				{empty,	empty,	1,		empty,	5,		8,		2,		empty,	6},
				{empty,	empty,	8,		6,		empty,	empty,	empty,	empty,	1},
				{empty,	2,		empty,	empty,	empty,	7,		empty,	5,		empty},
				{empty,	empty,	3,		7,		empty,	5,		empty,	2,		8},
				{empty,	8,		empty,	empty,	6,		empty,	7,		empty,	empty},
				{2,		empty,	7,		empty,	8,		3,		6,		1,		5}
				};
		
		
		field = new SudokuField(arrField);
		solver = new SudokuSolver(arrField);
	}
	
	@Test
	void testCheckLine() {
		assertTrue(SudokuSolver.checkLine(0, field, 5));
		assertFalse(SudokuSolver.checkLine(1, field, 7));
	}
	
	@Test
	void testCheckColumn() {
		assertTrue(SudokuSolver.checkColumn(3, field, 9));
		assertFalse(SudokuSolver.checkColumn(4, field, 5));
	}	
	
	@Test
	void testCheckBox() {
		assertTrue(SudokuSolver.checkBox(0,2, field, 6));
		assertFalse(SudokuSolver.checkBox(0,2, field, 1));
	}
	
	@Test
	void testCheckValidInput() {
		assertTrue(SudokuSolver.checkValidInput(0,2, field, 7));
		assertFalse(SudokuSolver.checkValidInput(0,2, field, 5));
	}

	@Test
	void testSolver() {
		solver.solve();
		for (int y = 0; y < field.COLLIN; y++) {
			for (int x = 0; x < field.COLLIN; x++) {
				assertFalse(field.compareValue(x, y, SudokuField.EMPTY));
			}
		}
	}
	
}
	
