package domain

import PLAYFIELD_3_EMPTY_X
import domain.entities.SudokuField
import domain.xSudoku.XSudokuGameFieldGenerator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class TestXSudokuGameFieldGenerator {
    lateinit var game: XSudokuGameFieldGenerator
    lateinit var field: SudokuField
    private lateinit var possibles: Array<Array<MutableSet<Int>>>

    @BeforeEach
    @Throws(java.lang.Exception::class)
    fun setUp() {
        val arrField = PLAYFIELD_3_EMPTY_X
        field = SudokuField(arrField)
        game = XSudokuGameFieldGenerator(field)
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
    fun testCheckForObviousDiagonal() {
        assertFalse(game.checkForObviousDiagonal(3, 3, 7))
    }
}
