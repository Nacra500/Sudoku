package view.game

import Screens
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Canvas
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.format.TextStyle
import kotlin.math.sqrt


@Composable
@Preview
fun GameView(vm: GameViewModel) {
    val gameUiState by vm.uiState.collectAsState()
    vm.loadGame()

    val modifier: Modifier = Modifier.padding(1.dp)

    MaterialTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            // End Game button
            Button(onClick = {
                vm.endGame()
            }) {
                Text("EndGame")
            }

            // Sudoku grid

            for (i in gameUiState.field.indices) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()

                    //horizontalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    for (j in gameUiState.field[i].indices) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .border(
                                    width = 0.3.dp,
                                    color = Color.Black,
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (gameUiState.field[i][j] == -1) ""
                                else gameUiState.field[i][j].toString(),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .height(20.dp)
                                    .fillMaxSize()


                            )
                        }
                    }
                }
            }



        }
    }
}

