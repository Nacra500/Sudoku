package view.game

import NavigationParcel
import NormalSudokuGame
import SudokuGame
import XSudokuGame
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import domain.GameMode

/**
 * The GameViewModel which orchestrates the user interface in regards of
 * - input actions
 * - loading game via the game engine
 * - ends game
 * - updates game states
 *
 * @param  MutableStateFlow<NavigationParcel>  Flow to communicate with main function and deliver achieved points
 */
class GameViewModel(var di: MutableStateFlow<NavigationParcel>) {

    private var _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private lateinit var engine: SudokuGame

    /**
     * Uses the Sudoku Generator to generate according
     * to the GameMode the gamefield and inserts it into the uiState
     *
     * @param  GameMode  the GameMode object that defines the to be loaded game
     */
    fun loadGame(modi: GameMode) {
        println(modi)
        clearPoints()
        updatePoints(modi.costs)
        engine = when(modi.name){
            "X-Sudoku" -> XSudokuGame(modi.selection.size.selected, modi.selection.difficulty.selected)
            else -> NormalSudokuGame(modi.selection.size.selected, modi.selection.difficulty.selected)
        }
        _uiState.update { currentState ->
            currentState.copy(field = engine.generate().field, fieldComplete = engine.solution.field, mode = modi, win = false)
        }
    }

    /**
     * Ends the game and navigates back to the menu with
     * the earned points information
     */
    fun endGame() {
        di.value = NavigationParcel.Menu(if(uiState.value.win) _uiState.value.points else 0)
    }

    /**
     * Updates the selected field in the uiState
     *
     * @param  Int  the X coordinate of the selection
     * @param  Int  the Y coordinate of the selection
     */
    fun selectField(i: Int, j: Int) {
        _uiState.update { currentState ->
            currentState.copy(selection = Pair(i, j))
        }
    }

    /**
     * Tackles user input via the keyboard
     * Actions are split in three categories represented by three keyMaps
     * numKeyMap: Filters for number input which updates the game field
     * navKeyMap: Filters for arrow input which updates the selection field to the next available one
     * deletionKey: Filters for backspace and deletes user input in selected field
     *
     * @param  Int  the identifier of the pressed key
     */
    fun keyEvent(key: Int): Boolean {
        _uiState.value.selection?.run{
            val field = _uiState.value.field
            println(key)
            numKeyMap[key]?.let {
                field[this.first][this.second] = -it
                updateField(field)
            } ?: navKeyMap[key]?.let {
                var newValue = this.moveField(it)
                while(newValue.testInBorders(field) && field[newValue.first][newValue.second] > 0){
                    newValue = newValue.moveField(it)
                }
                if(newValue.testInBorders(_uiState.value.field)){
                    selectField(newValue.first, newValue.second)
                }
            } ?: if(key == 127){
                field[this.first][this.second] = 0
                updateField(field)
            }else{ }
        }
        return true
    }

    /**
     * Updates the game field in the uiState to reflect changes accordingly
     *
     * @param  Array<Array<Int>>  the new game field to be set
     */
    fun updateField(newValue: Array<IntArray>){
        _uiState.update { currentState ->
            currentState.copy(field = newValue, render = !_uiState.value.render)
        }
    }

    /**
     * Updates the potential earnable XP for the user as they
     * are decreased over time
     *
     * @param  Int  difference of the points to be set
     */
    fun updatePoints(diff: Int){
        _uiState.update { currentState ->
            currentState.copy(points = uiState.value.points+diff)
        }
    }

    /**
     * Sets the points to zero
     */
     fun clearPoints(){
        _uiState.update { currentState ->
            currentState.copy(points = 0)
        }
    }

    /**
     * Checks if the user inputs equals the generated sudoku. Such equal comparison is possible as each Sudoku has only one
     * valid solution. If it was successfully the user can end the game and gets the points granted.
     */
    fun submit() {
        if(uiState.value.win){
            endGame()
        }else{
            if(uiState.value.fieldComplete.contentEquals(uiState.value.field)){
                _uiState.update { currentState ->
                    currentState.copy(win = true)
                }
            }
        }
    }
}

/**
 * @param Array<Array<Int>> the game field
 * @return boolean which states of selection is inside of game borders
 */
private fun Pair<Int, Int>.testInBorders(field: Array<IntArray>): Boolean = first in field.indices && second in 0 until field[0].size

/**
 * @param Int the arrow value of the keyevent
 * @return the new selection coordinates after going in direction of arrow keypress event
 */
private fun Pair<Int, Int>.moveField(key: Int) = when(key){
    1 -> this.copy(second = this.second - 1)
    2 -> this.copy(first = this.first - 1)
    3 -> this.copy(second = this.second + 1)
    4 -> this.copy(first = this.first + 1)
    else -> this
}

data class GameUiState(
    var field: Array<IntArray> = emptyArray<IntArray>(),
    var fieldComplete: Array<IntArray> = emptyArray<IntArray>(),
    var selection: Pair<Int, Int>? = null,
    val render: Boolean = false,
    val points: Int = 0,
    val mode: GameMode? = null,
    val win: Boolean = false
)

val numKeyMap: Map<Int, Int> = mapOf(97 to 1, 98 to 2, 99 to 3, 100 to 4, 101 to 5, 102 to 6, 103 to 7,  104 to 8, 105 to 9, 49 to 1, 50 to 2, 51 to 3, 52 to 4, 53 to 5, 54 to 6, 55 to 7, 56 to 8, 57 to 9)
val navKeyMap: Map<Int, Int> = mapOf(37 to 1, 38 to 2, 39 to 3, 40 to 4)