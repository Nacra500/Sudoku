package domain

/**
 * Definition of a Game Mode. It contains solid values like name and editable values like the options that
 * can be modified during the game. The options controll the user selection of size and difficulty for the game mode and the
 * availability of the selection. The option informations and the multiplicator as indicator of pricing are used to
 * calculate the costs of the game mode.
 * If the option selections are greater than the availability the costs are negative and increasing with the multiplicator.
 * If the option selections are in the borders of the availability the costs are positive and reflect the earnable points for solving the sudoku of that kind.
 */
abstract class GameMode{
    abstract val name: String
    abstract val mode: GameModes
    open val availableSizes: Array<SIZES> = SIZES.values()
    open val selection: Selection = Selection()
    open var available: Boolean = false
    abstract val multiplicator: Float
    val costs: Int
        get() {
            val diffAvailable = selection.difficulty.selected.ordinal <= selection.difficulty.available.ordinal
            val sizeAvailable = selection.size.selected.ordinal <= selection.size.available.ordinal
            return if(!available) -500 else ((
                    when{
                        diffAvailable && sizeAvailable -> multiplicator*(selection.difficulty.selected.ordinal+1+selection.size.selected.ordinal+1)
                        !diffAvailable && sizeAvailable -> if((selection.difficulty.selected.ordinal - selection.difficulty.available.ordinal) > 1) -multiplicator*(selection.difficulty.selected.ordinal+2.5) else -multiplicator*(selection.difficulty.selected.ordinal+1)
                        diffAvailable && !sizeAvailable -> if((selection.size.selected.ordinal - selection.size.available.ordinal) > 1) -multiplicator*(selection.size.selected.ordinal+2.5) else -multiplicator*(selection.size.selected.ordinal+1)
                        else -> -multiplicator*(selection.size.selected.ordinal+1+selection.difficulty.selected.ordinal+1)
                    }
                    ).toInt()*100)
        }
}

data class Selection(
    var size: GameOptionsItem<SIZES> = GameOptionsItem(SIZES.NORMAL, SIZES.NORMAL),
    var difficulty: GameOptionsItem<DIFFICULTIES> = GameOptionsItem(DIFFICULTIES.EASY, DIFFICULTIES.EASY)
)

data class GameOptionsItem<T>(var selected: T, var available: T)

enum class SIZES(val COLLIN: Int) {NORMAL(9), BIG(16)}
enum class DIFFICULTIES(val percentShown: Int){EASY(30), NORMAL(40), HARD(75)}
enum class GameModes { NORMAL, EVENODD, XSUDOKU }

class Mode1 : GameMode() {
    override val name = "Classic"
    override val mode = GameModes.NORMAL
    override var available = true
    override val multiplicator = 1f
}
class Mode2 : GameMode() {
    override val name: String = "Even-Odd"
    override val mode = GameModes.EVENODD
    override val multiplicator = 2f
}
class Mode3 : GameMode() {
    override val name: String = "X-Sudoku"
    override val mode = GameModes.XSUDOKU
    override val multiplicator = 3f
    override val availableSizes = arrayOf(SIZES.NORMAL)
}