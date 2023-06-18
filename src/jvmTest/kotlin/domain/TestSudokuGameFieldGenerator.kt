package domain

import PLAYFIELD_3_EMPTY
import domain.entities.SudokuField
import domain.game.SudokuGameFieldGenerator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class TestSudokuGameFieldGenerator {
    lateinit var game: SudokuGameFieldGenerator
    lateinit var field: SudokuField
    lateinit var possibles: Array<Array<MutableSet<Int>>>
    @BeforeEach
    @Throws(java.lang.Exception::class)
    fun setUp() {
        val arrField = PLAYFIELD_3_EMPTY
        field = SudokuField(arrField)
        game = SudokuGameFieldGenerator(field)
        possibles = Array(field.COLLIN) {
            Array(field.COLLIN) { mutableSetOf(0) }
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
