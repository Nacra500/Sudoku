import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class TestSudokuGameFieldGenerator {
    var game: SudokuGameFieldGenerator? = null
    var field: SudokuField? = null
    var possibles: Array<Array<MutableSet<Int>>>
    @BeforeEach
    @Throws(java.lang.Exception::class)
    fun setUp() {
        val empty: Int = SudokuField.EMPTY
        val arrField = arrayOf(
            intArrayOf(5, 3, 4, 6, 7, empty, 9, 1, 2),
            intArrayOf(6, empty, 2, 1, 9, 5, 3, 4, 8),
            intArrayOf(1, 9, 8, 3, 4, 2, empty, 6, 7),
            intArrayOf(8, 5, 9, empty, 6, 1, 4, 2, 3),
            intArrayOf(4, 2, 6, 8, 5, 3, 7, empty, 1),
            intArrayOf(7, 1, empty, 9, 2, 4, 8, 5, 6),
            intArrayOf(empty, 6, 1, 5, 3, 7, 2, 8, 4),
            intArrayOf(2, 8, 7, 4, empty, 9, 6, 3, 5),
            intArrayOf(3, 4, 5, 2, 8, 6, 1, empty, empty)
        )
        field = SudokuField(arrField)
        game = SudokuGameFieldGenerator(field)
        possibles = Array<Array<MutableSet<*>>>(9) {
            arrayOfNulls<Set<*>>(
                9
            )
        }
        for (i in 0..8) {
            for (j in 0..8) {
                possibles[i][j] = HashSet<Int>()
            }
        }
        possibles[6][0].add(9)
        possibles[1][1].add(7)
        possibles[5][2].add(3)
        possibles[3][3].add(7)
        possibles[7][4].add(1)
        possibles[0][5].add(8)
        possibles[2][6].add(5)
        possibles[4][7].add(9)
        possibles[8][8].add(9)
        possibles[8][7].add(9)
        possibles[8][7].add(7)
        possibles[4][7].add(9)
        possibles[8][8].add(9)
        game.possibles = possibles
    }

    @Test
    fun testCheckForObvious() {
        assertTrue(game.checkForObvious(7, 8, 7))
        assertFalse(game.checkForObvious(7, 8, 9))
    }

    @Test
    fun testCheckForObviousColumn() {
        assertTrue(game.checkForObviousColumn(7, 7))
        assertFalse(game.checkForObviousColumn(7, 9))
        assertFalse(game.checkForObviousColumn(0, 4))
    }

    @Test
    fun testCheckForObviousRow() {
        assertTrue(game.checkForObviousRow(8, 7))
        assertFalse(game.checkForObviousRow(8, 9))
        assertFalse(game.checkForObviousRow(0, 4))
    }

    @Test
    fun testCheckForObviousBox() {
        assertTrue(game.checkForObviousBox(7, 6, 7))
        assertFalse(game.checkForObviousBox(7, 6, 9))
    }
}
