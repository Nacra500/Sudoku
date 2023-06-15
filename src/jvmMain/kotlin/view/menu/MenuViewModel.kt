package view.menu

import NavigationParcel
import domain.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * The MenuViewModel which orchestrates the user interface in regards of
 * - selecting mode
 * - selecting size
 * - selecting difficulty
 * - buying new modes
 * - starting games
 *
 * @param  MutableStateFlow<NavigationParcel>  Flow to communicate with main function and deliver selected game mode
 */
class MenuViewModel(var di: MutableStateFlow<NavigationParcel>) {

    private var _uiState = MutableStateFlow(MenuUiState())
    val uiState: StateFlow<MenuUiState> = _uiState.asStateFlow()
    init {
        updateButton()
    }

    /**
     * Updates the XP the user has in the ui state
     *
     * @param  Int  difference to current xp status
     */
    fun updateXP(diff: Int) {
        _uiState.update { currentState ->
            currentState.copy(xp = _uiState.value.xp + diff)
        }
    }

    /**
     * Starts a new Game by setting the navigation to new Game with
     * the current selected Game mode as attribute
     */
    fun startGame() {
        di.value = NavigationParcel.Game(_uiState.value.selectedMode)
    }

    /**
     * Updates the selected size for the current selected game mode in the ui state
     * by finding the current selection in the displayed list of available game modes
     *
     * @param  GameMode  selected game mode for which new size is applicable
     * @param  SIZE  new chosen size as ENUM
     */
    fun updateSize(gameMode: GameMode, i: SIZES) {
        val newState = _uiState.value.copy()
        newState.gameModes.find { it.name == gameMode.name }?.selection?.size?.selected = i
        _uiState.update { currentState ->
            currentState.copy(gameModes = newState.gameModes, render = !newState.render)
        }
        updateGameMode(gameMode)
    }

    /**
     * Updates the selected difficulty for the current selected game mode in the ui state
     * by finding the current selection in the displayed list of available game modes
     *
     * @param  GameMode  selected game mode for which new size is applicable
     * @param  DIFFICULT  new chosen difficulty as ENUM
     */
    fun updateDifficult(gameMode: GameMode, i: DIFFICULTIES) {
        val newState = _uiState.value.copy()
        newState.gameModes.find { it.name == gameMode.name }?.selection?.difficulty?.selected = i
        _uiState.update { currentState ->
            currentState.copy(gameModes = newState.gameModes, render = !newState.render)
        }
        updateGameMode(gameMode)
    }

    /**
     * Updates the selected game mode in the ui state and triggers therefore
     * updating the button state
     *
     * @param  GameMode  the selected game mode
     */
    fun updateGameMode(gameMode: GameMode) {
        _uiState.update { currentState ->
            currentState.copy(selectedMode = gameMode)
        }
        updateButton()
    }

    /**
     * Evaluates what action to perform after button is pressed:
     * - game mode costs are positive -> start game
     * - game mode costs are negative and higher than xp -> do nothing
     * - game mode costs are negative and NOT higher than xp -> unlock the selected mode and subtract xp
     * */
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
                    this.selection.difficulty.available = this.selection.difficulty.selected
                    this.selection.size.available = this.selection.size.selected
                }
            }
            _uiState.update { currentState ->
                currentState.copy(gameModes = newState.gameModes, render = !newState.render)
            }
        }
        updateButton()
    }

    /**
     * Updates the button state to reflect the current costs of the selected game mode
     * Notice that positive costs are earnable xp and the mode is active
     *             negative costs are real costs that need to be spent to purchase new mode
     */
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