package domain

import PLAYFIELD_3_EMPTY_X
import domain.entities.SudokuField
import domain.xSudoku.XSudokuSolver
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class TestXSudokuSolver {
    lateinit var field: SudokuField

    @BeforeEach
    @Throws(java.lang.Exception::class)
    fun setUp() {
        val arrField = PLAYFIELD_3_EMPTY_X
        field = SudokuField(arrField)
    }

    @Test
    fun testCheckDiagonal() {
        assertTrue(XSudokuSolver.checkDiagonal(6, 2, field, 6))
        assertFalse(XSudokuSolver.checkDiagonal(6, 2, field, 7))
    }
}
