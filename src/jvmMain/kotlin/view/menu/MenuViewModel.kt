package view.menu

import NavigationParcel
import domain.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MenuViewModel(var di: MutableStateFlow<NavigationParcel>) {

    private var _uiState = MutableStateFlow(MenuUiState())
    val uiState: StateFlow<MenuUiState> = _uiState.asStateFlow()

    init {
        updateButton()
    }

    fun updateXP(diff: Int) {
        _uiState.update { currentState ->
            currentState.copy(xp = _uiState.value.xp + diff)
        }
    }

    fun startGame() {
        di.value = NavigationParcel.Game(_uiState.value.selectedMode)
    }

    fun updateSize(gameMode: GameMode, i: SIZES) {
        val newState = _uiState.value.copy()
        newState.gameModes.find { it.name == gameMode.name }?.options?.size?.selected = i
        _uiState.update { currentState ->
            currentState.copy(gameModes = newState.gameModes, render = !newState.render)
        }
        updateGameMode(gameMode)
    }

    fun updateDifficult(gameMode: GameMode, i: DIFFICULT) {
        val newState = _uiState.value.copy()
        newState.gameModes.find { it.name == gameMode.name }?.options?.difficult?.selected = i
        _uiState.update { currentState ->
            currentState.copy(gameModes = newState.gameModes, render = !newState.render)
        }
        updateGameMode(gameMode)
    }

    fun updateGameMode(gameMode: GameMode) {
        _uiState.update { currentState ->
            currentState.copy(selectedMode = gameMode)
        }
        updateButton()
    }

    fun buttonPressed() {
        if (_uiState.value.selectedMode.costs > 0) {
            startGame()
        } else if (-_uiState.value.selectedMode.costs <= _uiState.value.xp) {
            updateXP(_uiState.value.selectedMode.costs)
            val newState = _uiState.value.copy()
            newState.gameModes.find { it.name == _uiState.value.selectedMode.name }?.apply {
                if(!this.available){
                    this.available = true
                }else{
                    this.options.difficult.available = this.options.difficult.selected
                    this.options.size.available = this.options.size.selected
                }
            }
            _uiState.update { currentState ->
                currentState.copy(gameModes = newState.gameModes, render = !newState.render)
            }
        }
        updateButton()
    }

    fun updateButton(){
        _uiState.update { currentState ->
            currentState.copy(buttonState = _uiState.value.selectedMode.costs)
        }
    }
}

data class MenuUiState(
    var xp: Int = 0,
    var gameModes: List<GameMode> = listOf(Mode1(), Mode2(), Mode3()),
    var selectedMode: GameMode = Mode1(),
    var startGame: Boolean = false,
    val render: Boolean = false,
    val buttonState: Int = 0
)