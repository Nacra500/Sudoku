package view.game

import domain.GameEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel(var di: MutableStateFlow<Int>) {

    private var _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private lateinit var engine: GameEngine

    fun loadGame() {
        engine = GameEngine(4)
        _uiState.update { currentState ->
            currentState.copy(field = engine.createSolvable())
        }
    }

    fun endGame() {
        di.value = Screens.MENU.ordinal
    }
}

data class GameUiState(
    var field: Array<Array<Int>> = emptyArray<Array<Int>>(),
    var endGame: Boolean = false
)