package view.menu

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MenuViewModel(var di: MutableStateFlow<Int>) {

    private var _uiState = MutableStateFlow(MenuUiState())
    val uiState: StateFlow<MenuUiState> = _uiState.asStateFlow()

    fun updateXP(){
        _uiState.update { currentState ->
            currentState.copy(xp = _uiState.value.xp + 1)
        }
    }

    fun startGame() {
        di.value = Screens.GAME.ordinal
    }
}

data class MenuUiState(
    var xp: Int = 0,
    var startGame: Boolean = false
)