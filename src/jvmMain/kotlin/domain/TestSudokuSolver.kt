import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class TestSudokuSolver {
    var field: SudokuField? = null
    var solver: SudokuSolver? = null
    @BeforeEach
    @Throws(java.lang.Exception::class)
    fun setUp() {
        val empty: Int = SudokuField.EMPTY
        val arrField = arrayOf(
            intArrayOf(empty, empty, empty, empty, empty, empty, empty, empty, empty),
            intArrayOf(empty, 1, 2, empty, 3, 4, 5, 6, 7),
            intArrayOf(empty, 3, 4, 5, empty, 6, 1, 8, 2),
            intArrayOf(empty, empty, 1, empty, 5, 8, 2, empty, 6),
            intArrayOf(empty, empty, 8, 6, empty, empty, empty, empty, 1),
            intArrayOf(empty, 2, empty, empty, empty, 7, empty, 5, empty),
            intArrayOf(empty, empty, 3, 7, empty, 5, empty, 2, 8),
            intArrayOf(empty, 8, empty, empty, 6, empty, 7, empty, empty),
            intArrayOf(2, empty, 7, empty, 8, 3, 6, 1, 5)
        )
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
