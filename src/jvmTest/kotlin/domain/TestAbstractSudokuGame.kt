package domain

import domain.entities.SudokuCell
import domain.entities.SudokuField
import domain.game.AbstractSudokuGame
import normalSudoku.NormalSudokuGame
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class TestAbstractSudokuGame {
    private lateinit var game: AbstractSudokuGame
    @BeforeEach
    fun setup() {
        game = NormalSudokuGame(SIZES.NORMAL, DIFFICULTIES.HARD)
    }

    @Test
    fun testGenerate() {
        val field: SudokuField = game.generate()
        assertTrue(hasEmptyCells(field))
    }

    @Test
    fun testGetSolution() {
        game.generate()
        val field: SudokuField = game.solution
        assertFalse(hasEmptyCells(field))
    }

    @Test
    fun testGetHint() {
        val field: SudokuField = game.generate()
        val hint: SudokuCell = game.getHint(field)!!
        assertEquals(field.getCellValue(hint.x, hint.y), hint.`val`)
    }

    @Test
    fun testGetMistakes() {
        val gameField: SudokuField = game.generate()
        val field: SudokuField = game.solution
        var `val`: Int = field.getCellValue(0, 0)
        `val` = Math.abs(`val` - field.COLLIN)
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
