package domain

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

object Mode1 : GameMode() {
    override val name = "Klassiker"
    override var available = true
    override val multiplicator = 1f
}
object Mode2 : GameMode() {
    override val name: String = "Even-Odd"
    override val multiplicator = 2f
}
object Mode3 : GameMode() {
    override val name: String = "X-Sudoku"
    override val multiplicator = 3f
}