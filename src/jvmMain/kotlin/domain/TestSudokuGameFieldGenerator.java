import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestSudokuGameFieldGenerator {

	SudokuGameFieldGenerator game;
	SudokuField field;
	Set<Integer>[][] possibles;


	@BeforeEach
	void setUp() throws Exception {
		int empty = SudokuField.EMPTY;
		int[][] arrField = {
				{5,	3,	4,	6,	7,	empty,	9,	1,	2},
				{6, empty,	2,	1,	9,	5,	3,	4,	8},
				{1, 9,	8,	3,	4,	2,	empty,	6,	7},
				{8,	5,	9,	empty,	6,	1,	4,	2,	3},
				{4,	2,	6,	8,	5,	3,	7,	empty,	1},
				{7,	1,	empty,	9,	2,	4,	8,	5,	6},
				{empty,	6,	1,	5,	3,	7,	2,	8,	4},
				{2,	8,	7,	4,	empty,	9,	6,	3,	5},
				{3,	4,	5,	2,	8,	6,	1,	empty,	empty}
				};
		
		field = new SudokuField(arrField);
		game = new SudokuGameFieldGenerator(field);
		possibles = new Set[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                possibles[i][j] = new HashSet<Integer>();
            }
        }
        possibles[6][0].add(9);
        possibles[1][1].add(7);
        possibles[5][2].add(3);
        possibles[3][3].add(7);
        possibles[7][4].add(1);
        possibles[0][5].add(8);
        possibles[2][6].add(5);
        possibles[4][7].add(9);
        possibles[8][8].add(9);
        possibles[8][7].add(9);
        possibles[8][7].add(7);
        possibles[4][7].add(9);
        possibles[8][8].add(9);
        game.possibles = possibles;
	}


	@Test
	void testCheckForObvious() {
		assertTrue(game.checkForObvious(7, 8, 7));
		assertFalse(game.checkForObvious(7, 8, 9));
	}

	@Test
	void testCheckForObviousColumn() {
		assertTrue(game.checkForObviousColumn(7, 7));
		assertFalse(game.checkForObviousColumn(7, 9));
		assertFalse(game.checkForObviousColumn(0, 4));

	}	
	
	@Test
	void testCheckForObviousRow() {
		assertTrue(game.checkForObviousRow(8, 7));
		assertFalse(game.checkForObviousRow(8, 9));
		assertFalse(game.checkForObviousRow(0, 4));
	}
	
	@Test
	void testCheckForObviousBox() {
		assertTrue(game.checkForObviousBox(7, 6, 7));
		assertFalse(game.checkForObviousBox(7, 6, 9));
	}
}
