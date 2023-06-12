package view.game

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import values.ComposeStyles
import kotlin.math.absoluteValue
import kotlin.math.sqrt


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

            CountUpTimer()
            // Sudoku grid
            SudokuGrid(gameUiState.field, gameUiState.selection, vm)

            Button(onClick = {
                vm.endGame()
            }, modifier = Modifier.align(Alignment.End)
            ) {
                Text("EndGame")
            }

        }
    }
}

@Composable
fun SudokuGrid(field: Array<Array<Int>>, selection: Pair<Int, Int>?, vm: GameViewModel) {

    with(ComposeStyles.SudokuGridStyles) {

        val gridSize = field.size
        val subGridSize = sqrt(gridSize.toDouble()).toInt()
        val gridWidth = ((boxSize.value + normalBorderWidth.value) * gridSize + thickBorderWidth.value * subGridSize).dp

        for (i in field.indices) {
            if (i % subGridSize == 0) GridDivider(thickBorderWidth, gridWidth)
            Row() {
                for (j in field[i].indices) {
                    if (j % subGridSize == 0) {
                        GridDivider(boxSize, thickBorderWidth)
                    }
                    (field[i][j] <= 0).let { fillAble ->
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .border(
                                    width = normalBorderWidth,
                                    color = Color.Black,
                                ).run {
                                    if (fillAble) this.clickable { vm.selectField(i, j) } else this
                                }.run {
                                    if (selection?.first == i && selection.second == j) this.background(
                                        Color(0xFFffe0b3)
                                    ) else this
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                fontWeight = if (!fillAble) FontWeight.ExtraBold else null,
                                text = if (field[i][j] == 0) ""
                                else field[i][j].absoluteValue.toString(),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .height(textSize)
                                    .fillMaxSize(),
                                color = if (fillAble) textColorPrimary else textColorSecondary
                            )
                        }
                    }
                }
                GridDivider(boxSize, thickBorderWidth)
            }
        }
        GridDivider(thickBorderWidth, gridWidth)
    }
}

@Composable
fun GridDivider(heightDp: Dp, widthDp: Dp){
    Divider(
        modifier = Modifier
            .height(heightDp)
            .background(ComposeStyles.SudokuGridStyles.borderColor)
            .width(widthDp)
    )
}


/**
 * A Jetpack Compose function to display a count up timer.
 *
 * The function initializes a timer that starts from zero and counts upwards.
 * The timer updates every second and the displayed time is formatted in
 * the "MM:SS" format. The timer runs indefinitely.
 *
 * Note: This Composable function uses a LaunchedEffect coroutine which isn't
 * suitable for long-running or background tasks.
 *
 * Example usage: CountUpTimer()
 *
 * @see androidx.compose.runtime.LaunchedEffect
 * @see androidx.compose.runtime.remember
 * @see androidx.compose.runtime.mutableStateOf
 * @see androidx.compose.runtime.derivedStateOf
 */
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

