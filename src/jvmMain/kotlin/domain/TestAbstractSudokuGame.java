import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestAbstractSudokuGame {
	
	AbstractSudokuGame game;
	
	@BeforeEach
	void setup() {
		game = new NormalSudokuGame(GameFactory.Size.MEDIUM, GameFactory.Difficulty.HARD);
	}

	@Test
	void testGenerate() {
		SudokuField field = game.generate();
		assertTrue(hasEmptyCells(field));
	}
	
	void testGetSolution() {
		game.generate();
		SudokuField field = game.getSolution();
		assertFalse(hasEmptyCells(field));
	}
	
	void testGetHint() {
		game.generate();
		SudokuField field = game.getSolution();
		SudokuCell hint = game.getHint(field);
		assertEquals(field.getCellValue(hint.x, hint.y), hint.val);
	}
	
	void testGetMistakes() {
		SudokuField gameField = game.generate();
		SudokuField field = game.getSolution();
		int val = field.getCellValue(0, 0);
		val = Math.abs(val - field.COLLIN);
		gameField.setValue(0, 0, val);
		
		List<SudokuCell> mistakes = game.showMistakes(gameField);
		assertEquals(mistakes.size(), 1);
		assertEquals(mistakes.get(0).x, 0);
		assertEquals(mistakes.get(0).y, 0);
		assertEquals(mistakes.get(0).val, field.getCellValue(0, 0));
	}
	
	private boolean hasEmptyCells(SudokuField field) {
		for (int y = 0; y < field.COLLIN; y++) {
			for (int x = 0; x < field.COLLIN; x++) {
				if (field.compareValue(x, y, SudokuField.EMPTY)) return true;
			}
		}
		return false;
	}
}
