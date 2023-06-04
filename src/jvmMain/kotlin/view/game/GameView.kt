package view.game

import Screens
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
@Preview
fun GameView(vm: GameViewModel) {
    val gameUiState by vm.uiState.collectAsState()

    val modifier: Modifier = Modifier.padding(1.dp)

    MaterialTheme {
        Row {
            Button(onClick = {
                vm.endGame()
            }) {
                Text("EndGame")
            }
        }
    }
}