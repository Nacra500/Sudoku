import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class TestXSudokuSolver {
    var field: SudokuField? = null
    @BeforeEach
    @Throws(java.lang.Exception::class)
    fun setUp() {
        val empty: Int = SudokuField.EMPTY
        val arrField = arrayOf(
            intArrayOf(5, 3, 4, 6, 7, empty, 9, 1, 2),
            intArrayOf(6, empty, 2, 1, 9, 5, 3, 4, 8),
            intArrayOf(1, 9, 8, 3, 4, 2, empty, 6, 7),
            intArrayOf(8, 5, 9, empty, 6, 7, 4, 2, 3),
            intArrayOf(4, 2, 6, 8, 5, 3, 7, empty, 1),
            intArrayOf(7, 1, empty, 9, 2, 4, 8, 5, 6),
            intArrayOf(empty, 6, 1, 5, 3, 7, 2, 8, 4),
            intArrayOf(2, 8, 7, 4, empty, 9, 6, 3, 5),
            intArrayOf(3, 4, 5, 2, 8, 6, 1, empty, empty)
        )
        field = SudokuField(arrField)
    }

    @Test
    fun testCheckDiagonal() {
        assertTrue(XSudokuSolver.checkDiagonal(6, 2, field, 6))
        assertFalse(XSudokuSolver.checkDiagonal(6, 2, field, 7))
    }
}
