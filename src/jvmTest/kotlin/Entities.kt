import domain.DIFFICULTIES
import domain.Mode1
import domain.SIZES
import domain.entities.SudokuField

val GAME_MODE = Mode1()
val GAME_MODE_BIG = Mode1().apply { selection.size.selected = SIZES.BIG }
val GAME_MODE_HARD = Mode1().apply { selection.difficulty.selected = DIFFICULTIES.HARD }
val GAME_MODE_BIG_BOUGHT = Mode1().apply { selection.size.available = SIZES.BIG }

val PLAYFIELD_3: Array<IntArray> = arrayOf(
    intArrayOf(8, 0, 3, 0, 9, 4, 7, 5, 1),
    intArrayOf(6, 5, 9, 0, 1, 2, 4, 3, 8),
    intArrayOf(7, 0, 4, 3, 5, 8, 6, 2, 9),
    intArrayOf(3, 4, 1, 0, 6, 9, 8, 7, 2),
    intArrayOf(2, 9, 7, 1, 8, 3, 5, 6, 4),
    intArrayOf(5, 8, 6, 2, 4, 7, 9, 1, 3),
    intArrayOf(9, 0, 2, 0, 7, 6, 1, 4, 5),
    intArrayOf(1, 7, 8, 4, 2, 5, 3, 9, 6),
    intArrayOf(4, 6, 5, 9, 3, 1, 2, 8, 7),
)

val PLAYFIELD_3_INPUT: Array<IntArray> = arrayOf(
    intArrayOf(8, -1, 3, 0, 9, 4, 7, 5, 1),
    intArrayOf(6, 5, 9, 0, 1, 2, 4, 3, 8),
    intArrayOf(7, 0, 4, 3, 5, 8, 6, 2, 9),
    intArrayOf(3, 4, 1, 0, 6, 9, 8, 7, 2),
    intArrayOf(2, 9, 7, 1, 8, 3, 5, 6, 4),
    intArrayOf(5, 8, 6, 2, 4, 7, 9, 1, 3),
    intArrayOf(9, 0, 2, 0, 7, 6, 1, 4, 5),
    intArrayOf(1, 7, 8, 4, 2, 5, 3, 9, 6),
    intArrayOf(4, 6, 5, 9, 3, 1, 2, 8, 7),
)

val empty: Int = SudokuField.EMPTY
val PLAYFIELD_3_EMPTY = arrayOf(
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

val PLAYFIELD_3_EMPTY_V2 = arrayOf(
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

val PLAYFIELD_3_EMPTY_X = arrayOf(
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