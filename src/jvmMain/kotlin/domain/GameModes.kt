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
    val options: GameOptions = GameOptions()
    open var available: Boolean = false
    abstract val multiplicator: Float
    val costs: Int
        get() {
            val diffAvailable = options.difficult.selected.ordinal <= options.difficult.available.ordinal
            val sizeAvailable = options.size.selected.ordinal <= options.size.available.ordinal
            return if(!available) -500 else ((
                    when{
                        diffAvailable && sizeAvailable -> multiplicator*(options.difficult.selected.ordinal+1+options.size.selected.ordinal+1)
                        !diffAvailable && sizeAvailable -> if((options.difficult.selected.ordinal - options.difficult.available.ordinal) > 1) -multiplicator*(options.difficult.selected.ordinal+2.5) else -multiplicator*(options.difficult.selected.ordinal+1)
                        diffAvailable && !sizeAvailable -> if((options.size.selected.ordinal - options.size.available.ordinal) > 1) -multiplicator*(options.size.selected.ordinal+2.5) else -multiplicator*(options.size.selected.ordinal+1)
                        else -> -multiplicator*(options.size.selected.ordinal+1+options.difficult.selected.ordinal+1)
                    }
                    ).toInt()*100)
        }
}

data class GameOptions(
    var size: GameOptionsItem<SIZES> = GameOptionsItem(SIZES.SMALL, SIZES.SMALL),
    var difficult: GameOptionsItem<DIFFICULT> = GameOptionsItem(DIFFICULT.EASY, DIFFICULT.EASY)
)

data class GameOptionsItem<T>(var selected: T, var available: T)

enum class SIZES{SMALL, NORMAL, BIG}
enum class DIFFICULT{EASY, NORMAL, HARD}

class Mode1 : GameMode() {
    override val name = "Klassiker"
    override var available = true
    override val multiplicator = 1f
}
class Mode2 : GameMode() {
    override val name: String = "Even-Odd"
    override val multiplicator = 2f
}
class Mode3 : GameMode() {
    override val name: String = "X-Sudoku"
    override val multiplicator = 3f
}