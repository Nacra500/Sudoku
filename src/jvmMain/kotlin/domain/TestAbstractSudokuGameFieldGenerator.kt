import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class TestAbstractSudokuGameFieldGenerator {
    var game: AbstractSudokuGameFieldGenerator? = null
    var field: SudokuField? = null
    var possible: Array<Array<MutableSet<Int>>>
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
        possible = Array<Array<MutableSet<*>>>(9) {
            arrayOfNulls<Set<*>>(9)
        }
        for (i in 0..8) {
            for (j in 0..8) {
                possible[i][j] = HashSet<Int>()
            }
        }
        possible[0][5].add(8)
        possible[1][1].add(7)
        possible[2][6].add(5)
        possible[3][3].add(7)
        possible[4][7].add(9)
        possible[5][2].add(3)
        possible[6][0].add(9)
        possible[7][4].add(1)
        possible[8][7].add(7)
        possible[8][7].add(9)
        possible[8][8].add(9)
    }

    @Test
    fun testCreateSolvable() {
        game.createSolvable()
        assertTrue(game.getNumberOfRemovedCells() > 0)
        assertTrue(game.getNumberOfRemovedCells() < 81 - 17) // 17 is the lowest number of cells a sudoku has to have in order to be solvable
    }

    @Test
    fun testAnalyzeField() {
        game.analyzeField()
        assertTrue(possibleEqual(game.possibles, possible))
        possible[8][8].add(3)
        assertFalse(possibleEqual(game.possibles, possible))
    }

    private fun possibleEqual(pos1: Array<Array<Set<Int>>>, pos2: Array<Array<MutableSet<Int>>>): Boolean {
        for (y in possible.indices) {
            for (x in possible[0].indices) {
                if (pos1[y][x] != pos2[y][x]) return false
            }
        }
        return true
    }
}
