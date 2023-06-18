package domain

import PLAYFIELD_3_EMPTY
import domain.entities.SudokuField
import domain.game.AbstractSudokuGameFieldGenerator
import domain.game.SudokuGameFieldGenerator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class TestAbstractSudokuGameFieldGenerator {
    lateinit var game: AbstractSudokuGameFieldGenerator
    lateinit var field: SudokuField
    lateinit var possible: Array<Array<MutableSet<Int>>>

    @BeforeEach
    @Throws(java.lang.Exception::class)
    fun setUp() {
        val arrField = PLAYFIELD_3_EMPTY
        field = SudokuField(arrField)
        game = SudokuGameFieldGenerator(field)
        possible = Array(field.COLLIN) {
            Array(field.COLLIN) { mutableSetOf(0) }
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
        assertTrue(game.numberOfRemovedCells > 0)
        assertTrue(game.numberOfRemovedCells < 81 - 17) // 17 is the lowest number of cells a sudoku has to have in order to be solvable
    }

    @Test
    fun testAnalyzeField() {
        game.analyzeField()
        assertTrue(possibleEqual(game.possibles, possible))
        possible[8][8].add(3)
        assertFalse(possibleEqual(game.possibles, possible))
    }

    private fun possibleEqual(pos1: Array<Array<MutableSet<Int>>>, pos2: Array<Array<MutableSet<Int>>>): Boolean {
        for (y in possible.indices) {
            for (x in possible[0].indices) {
                if (pos1[y][x] != pos2[y][x]) return false
            }
        }
        return true
    }
}
