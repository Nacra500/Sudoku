package view

import GAME_MODE
import GAME_MODE_BIG
import GAME_MODE_BIG_BOUGHT
import GAME_MODE_HARD
import NavigationParcel
import domain.DIFFICULTIES
import domain.SIZES
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import view.menu.MenuViewModel

class MenuViewModelTest {

    val navigationDummy: MutableStateFlow<NavigationParcel> = MutableStateFlow(NavigationParcel.Menu())

    lateinit var underTest: MenuViewModel

    @BeforeEach
    fun setUp() {
        underTest = MenuViewModel(navigationDummy)
    }

    @Test
    fun updateXP() {
        underTest.updateXP(50)

        assertEquals(50, underTest.uiState.value.xp)
    }

    @Test
    fun startGame() {
        underTest.startGame()

        assertEquals(NavigationParcel.Game(GAME_MODE).javaClass, navigationDummy.value.javaClass)
    }

    @Test
    fun updateSize() {
        underTest.updateSize(GAME_MODE, SIZES.BIG)

        assertEquals(GAME_MODE_BIG.selection.size, underTest.uiState.value.gameModes[0].selection.size)
    }

    @Test
    fun updateDifficult() {
        underTest.updateDifficult(GAME_MODE, DIFFICULTIES.HARD)

        assertEquals(GAME_MODE_HARD.selection.difficulty, underTest.uiState.value.gameModes[0].selection.difficulty)
    }

    @Test
    fun updateGameMode() {
        underTest.updateGameMode(GAME_MODE_BIG)

        assertEquals(GAME_MODE_BIG, underTest.uiState.value.selectedMode)
    }

    @Nested
    inner class ButtonPressed {

        @Test
        fun positiveAmount(){
            underTest.buttonPressed()

            assertEquals(NavigationParcel.Game(GAME_MODE).javaClass, navigationDummy.value.javaClass)
        }

        @Test
        fun negativeAmountTooExpensive(){
            underTest.updateGameMode(GAME_MODE_BIG)

            underTest.buttonPressed()

            assertEquals(0, underTest.uiState.value.xp)
        }

        @Test
        fun negativeAmountBuying(){
            underTest.updateSize(GAME_MODE, SIZES.BIG)
            underTest.updateXP(-GAME_MODE_BIG.costs)

            underTest.buttonPressed()

            assertEquals(GAME_MODE_BIG_BOUGHT.selection.size.available, underTest.uiState.value.gameModes[0].selection.size.selected)
        }
    }

    @Test
    fun updateButton() {
        underTest.updateButton()

        assertEquals(GAME_MODE.costs, underTest.uiState.value.buttonState)
    }
}