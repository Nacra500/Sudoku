package view

import GAME_MODE
import NavigationParcel
import PLAYFIELD_3
import PLAYFIELD_3_INPUT
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import view.game.GameViewModel


class GameViewModelTest {

    val navigationDummy: MutableStateFlow<NavigationParcel> = MutableStateFlow(NavigationParcel.Game(GAME_MODE))

    lateinit var underTest: GameViewModel

    @BeforeEach
    fun setup(){
        underTest = GameViewModel(navigationDummy)
        underTest.updateField(PLAYFIELD_3)
    }

    @Test
    fun loadGame() {
        underTest.loadGame(GAME_MODE)

        assertEquals(200, underTest.uiState.value.points)
    }

    @Test
    fun endGame() {
        underTest.updatePoints(50)

        underTest.endGame()

        assertEquals(NavigationParcel.Menu(50), navigationDummy.value)
    }

    @Test
    fun selectField() {
        underTest.selectField(0, 0)

        assertEquals(Pair(0, 0), underTest.uiState.value.selection)
    }


    @Nested
    inner class KeyEvent{

        @Test
        fun inputNumber() {
            underTest.selectField(0, 1)

            underTest.keyEvent(97)

            assertEquals(-1, underTest.uiState.value.field[0][1])
        }

        @Test
        fun inputArrow() {
            underTest.selectField(0, 0)

            underTest.keyEvent(39)

            assertEquals(Pair(0, 1), underTest.uiState.value.selection)
        }

        @Test
        fun inputDelete() {
            underTest.selectField(0, 1)
            underTest.updateField(PLAYFIELD_3_INPUT)

            underTest.keyEvent(127)

            assertEquals(0, underTest.uiState.value.field[0][1])
        }

    }

    @Test
    fun updateField() {
        underTest.selectField(0, 0)

        underTest.updateField(PLAYFIELD_3_INPUT)

        assertEquals(PLAYFIELD_3_INPUT, underTest.uiState.value.field)
    }

    @Test
    fun updatePoints() {
        underTest.updatePoints(50)

        assertEquals(50, underTest.uiState.value.points)
    }

    @Test
    fun clearPoints() {
        underTest.updatePoints(50)

        underTest.clearPoints()

        assertEquals(0, underTest.uiState.value.points)
    }
}
