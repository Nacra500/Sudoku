import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class TestAbstractSudokuGame {
    var game: AbstractSudokuGame? = null
    @BeforeEach
    fun setup() {
        game = NormalSudokuGame(GameFactory.Size.MEDIUM, GameFactory.Difficulty.HARD)
    }

    @Test
    fun testGenerate() {
        val field: SudokuField = game.generate()
        assertTrue(hasEmptyCells(field))
    }

    fun testGetSolution() {
        game.generate()
        val field: SudokuField = game.getSolution()
        assertFalse(hasEmptyCells(field))
    }

    fun testGetHint() {
        game.generate()
        val field: SudokuField = game.getSolution()
        val hint: SudokuCell = game.getHint(field)
        assertEquals(field.getCellValue(hint.x, hint.y), hint.`val`)
    }

    fun testGetMistakes() {
        val gameField: SudokuField = game.generate()
        val field: SudokuField = game.getSolution()
        var `val`: Int = field.getCellValue(0, 0)
        `val` = java.lang.Math.abs(`val` - field.COLLIN)
        gameField.setValue(0, 0, `val`)
        val mistakes: List<SudokuCell> = game.showMistakes(gameField)
        assertEquals(mistakes.size, 1)
        assertEquals(mistakes[0].x, 0)
        assertEquals(mistakes[0].y, 0)
        assertEquals(mistakes[0].`val`, field.getCellValue(0, 0))
    }

    private fun hasEmptyCells(field: SudokuField): Boolean {
        for (y in 0 until field.COLLIN) {
            for (x in 0 until field.COLLIN) {
                if (field.compareValue(x, y, SudokuField.EMPTY)) return true
            }
        }
        return false
    }
}
