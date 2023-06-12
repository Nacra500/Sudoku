package view.game

import NavigationParcel
import domain.SudokuGenerator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import domain.GameMode

class GameViewModel(var di: MutableStateFlow<NavigationParcel>) {

    private var _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private lateinit var engine: SudokuGenerator

    fun loadGame(modi: GameMode) {
        println(modi)
        clearPoints()
        updatePoints(modi.costs)
        engine = SudokuGenerator()
        engine.TimsSudokuField2(3)
        _uiState.update { currentState ->
            currentState.copy(field = engine.field)
        }
    }

    fun endGame() {
        di.value = NavigationParcel.Menu(_uiState.value.points)
    }

    fun selectField(i: Int, j: Int) {
        _uiState.update { currentState ->
            currentState.copy(selection = Pair(i, j))
        }
    }

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

    private fun updateField(newValue: Array<Array<Int>>){
        _uiState.update { currentState ->
            currentState.copy(field = newValue, render = !_uiState.value.render)
        }
    }

    private fun updatePoints(diff: Int){
        _uiState.update { currentState ->
            currentState.copy(points = uiState.value.points+diff)
        }
    }
    private fun clearPoints(){
        _uiState.update { currentState ->
            currentState.copy(points = 0)
        }
    }

}

private fun Pair<Int, Int>.testInBorders(field: Array<Array<Int>>): Boolean = first in field.indices && second in 0 until field[0].size
private fun Pair<Int, Int>.moveField(key: Int) = when(key){
    1 -> this.copy(second = this.second - 1)
    2 -> this.copy(first = this.first - 1)
    3 -> this.copy(second = this.second + 1)
    4 -> this.copy(first = this.first + 1)
    else -> this
}

data class GameUiState(
    var field: Array<Array<Int>> = emptyArray<Array<Int>>(),
    var selection: Pair<Int, Int>? = null,
    var endGame: Boolean = false,
    val render: Boolean = false,
    val points: Int = 0,
)

val numKeyMap: Map<Int, Int> = mapOf(97 to 1, 98 to 2, 99 to 3, 100 to 4, 101 to 5, 102 to 6, 103 to 7,  104 to 8, 105 to 9, 49 to 1, 50 to 2, 51 to 3, 52 to 4, 53 to 5, 54 to 6, 55 to 7, 56 to 8, 57 to 9)
val navKeyMap: Map<Int, Int> = mapOf(37 to 1, 38 to 2, 39 to 3, 40 to 4)