package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import SudokuField;
import SudokuSolver;

class TestSudokuSolver {
	
	SudokuSolver solver;
	SudokuField field;

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
		solver = new SudokuSolver(field);
	}

	@Test
	void testCheckLine() {
		assertTrue(SudokuSolver.checkLine(0, field, 5));
		assertFalse(SudokuSolver.checkLine(1, field, 7));
	}
}
	
