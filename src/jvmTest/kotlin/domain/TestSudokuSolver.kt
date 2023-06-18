package domain

import PLAYFIELD_3_EMPTY_V2
import domain.entities.SudokuField
import domain.solver.SudokuSolver
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class TestSudokuSolver {
    lateinit var field: SudokuField
    lateinit var solver: SudokuSolver
    @BeforeEach
    @Throws(java.lang.Exception::class)
    fun setUp() {
        val arrField = PLAYFIELD_3_EMPTY_V2
        field = SudokuField(arrField)
        solver = SudokuSolver(field)
    }

    @Test
    fun testCheckLine() {
        assertTrue(SudokuSolver.checkLine(0, field, 5))
        assertFalse(SudokuSolver.checkLine(1, field, 7))
    }

    @Test
    fun testCheckColumn() {
        assertTrue(SudokuSolver.checkColumn(3, field, 9))
        assertFalse(SudokuSolver.checkColumn(4, field, 5))
    }

    @Test
    fun testCheckBox() {
        assertTrue(SudokuSolver.checkBox(0, 2, field, 6))
        assertFalse(SudokuSolver.checkBox(0, 2, field, 1))
    }

    @Test
    fun testCheckValidInput() {
        assertTrue(SudokuSolver.checkValidInput(0, 2, field, 7))
        assertFalse(SudokuSolver.checkValidInput(0, 2, field, 5))
    }

    @Test
    fun testSolver() {
        solver.solve()
        field.displayInConsole()
        for (y in 0 until field.COLLIN) {
            for (x in 0 until field.COLLIN) {
                assertFalse(field.compareValue(x, y, SudokuField.EMPTY))
            }
        }
    }
}
