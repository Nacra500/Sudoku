package view.game

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlin.math.absoluteValue
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay


@Composable
@Preview
fun GameView(vm: GameViewModel) {
    val gameUiState by vm.uiState.collectAsState()
    vm.loadGame()

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

            CountUpTimer()

            // Sudoku grid

            for (i in gameUiState.field.indices) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()

                    //horizontalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    for (j in gameUiState.field[i].indices) {
                        (gameUiState.field[i][j] <= 0).let { fillAble ->
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .border(
                                        width = 0.3.dp,
                                        color = Color.Black,
                                    ).run {
                                        if(fillAble) this.clickable { vm.selectField(i, j) } else this
                                    }.run {
                                        if(gameUiState.selection?.first == i && gameUiState.selection?.second == j) this.background(Color(0xFFffe0b3)) else this
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    fontWeight = if (!fillAble) FontWeight.ExtraBold else null,
                                    text = if (gameUiState.field[i][j] == 0) ""
                                    else gameUiState.field[i][j].absoluteValue.toString(),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .height(20.dp)
                                        .fillMaxSize(),
                                    color = if (fillAble) Color(0xFF1a1a1a) else Color(0xFF000000)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun CountUpTimer() {
    var timePassed by remember { mutableStateOf(0) }
    val timerText = remember {
        derivedStateOf {
            String.format("%02d:%02d", timePassed / 60, timePassed % 60)
        }
    }

    LaunchedEffect(key1 = true) {
        while (true) {
            delay(1000L)
            timePassed++
        }
    }

    Text(
        text = timerText.value,
        fontSize = 30.sp,
        modifier = Modifier.padding(16.dp)
    )
}
