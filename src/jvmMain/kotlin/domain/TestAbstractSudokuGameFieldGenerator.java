import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestAbstractSudokuGameFieldGenerator {

	AbstractSudokuGameFieldGenerator game;
	SudokuField field;
	Set<Integer>[][] possible;
	
	
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
		possible = new Set[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                possible[i][j] = new HashSet<Integer>();
            }
        }
        possible[0][5].add(8);
        possible[1][1].add(7);
        possible[2][6].add(5);
        possible[3][3].add(7);
        possible[4][7].add(9);
        possible[5][2].add(3);
        possible[6][0].add(9);
        possible[7][4].add(1);
        possible[8][7].add(7);
        possible[8][7].add(9);
        possible[8][8].add(9);
	}


	@Test
	void testCreateSolvable() {
		game.createSolvable();
		assertTrue(game.getNumberOfRemovedCells() > 0);
		assertTrue(game.getNumberOfRemovedCells() < 81 -17); // 17 is the lowest number of cells a sudoku has to have in order to be solvable
	}
	
	@Test
	void testAnalyzeField() {
		game.analyzeField();
		assertTrue(possibleEqual(game.possibles, possible));
		possible[8][8].add(3);
		
		assertFalse(possibleEqual(game.possibles, possible));
	}
	
	private boolean possibleEqual(Set<Integer>[][] pos1, Set<Integer>[][] pos2) {
		for (int y = 0; y < possible.length; y++) {
			for (int x = 0; x < possible[0].length; x++) {
				if (!pos1[y][x].equals( pos2[y][x])) return false;
			}
		}
		return true;
	}


}
