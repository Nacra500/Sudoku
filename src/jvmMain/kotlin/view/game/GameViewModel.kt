package view.game

import domain.GameEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel(var di: MutableStateFlow<Int>) {

    private var _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private val engine = GameEngine()

    fun loadGame() {
        _uiState.update { currentState ->
            currentState.copy(field = engine.setupGame())
        }
    }

    fun endGame() {
        di.value = Screens.MENU.ordinal
    }
}

data class GameUiState(
    var field: List<List<Int>> = emptyList(),
    var endGame: Boolean = false
)