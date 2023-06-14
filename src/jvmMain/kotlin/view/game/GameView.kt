package view.game

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue
import kotlin.math.sqrt
import kotlin.random.Random
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.Button
import androidx.compose.ui.window.Popup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset

import domain.SudokuGenerator
import org.jetbrains.skia.paragraph.TextBox

@Composable
@Preview
fun GameView(vm: GameViewModel) {
    val gameUiState by vm.uiState.collectAsState()
    var popupControl by remember { mutableStateOf(false) }

    MaterialTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) { //title
                Text("Sudoku")
            }
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {

                    // Sudoku grid (add a mode input to choose the function)
                    val mode = "EvenOdd"
                    val difficulty = 3

                    when (mode) {
                        // Generate a random value once
                        "EvenOdd" -> SudokuGridEven(gameUiState.field, gameUiState.selection, vm, difficulty)
                        "X" -> SudokuGridX(gameUiState.field, gameUiState.selection, vm)
                        else -> SudokuGrid(gameUiState.field, gameUiState.selection, vm)
                    }

                    /*if (popupControl) {
                        Popup(
                            alignment = Alignment.Center,
                            offset = IntOffset(-80, 600),
                            onDismissRequest = { popupControl = false },

                            ) {
                            Surface(
                                border = BorderStroke(4.dp, MaterialTheme.colors.secondary),
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xCCEEEEEE),
                            ) {
                                Column(
                                    modifier = Modifier.padding(20.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = "I'm popping up")
                                    Spacer(modifier = Modifier.height(32.dp))
                                    TextButton(onClick = { popupControl = false }) {
                                        Text(text = "Close Popup")
                                    }
                                }
                            }
                        }*/
                }

                Column {
                    CountUpTimer()
                    EarningPoints(vm.uiState.value.points)

                    TextButton(onClick = { popupControl = true }) {
                        Text("Hint")
                    }

                    Button(
                        onClick = {
                            //vm.submit()
                        }, modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Submit")
                    }

                    Button(
                        onClick = {
                            vm.endGame()
                        }, modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("EndGame")
                    }
                }
            }
        }
    }
}


@Composable
fun displayHint(){

}

@Composable
fun buyHint(){

}

@Composable
fun EarningPoints(points: Int){
    var timePassed by remember { mutableStateOf(0) }
    LaunchedEffect(key1 = true) {
        while (true) {
            delay(60000L)
            timePassed++
        }
    }

    Text(
        text = "Points: "+(points-timePassed*10),
        fontSize = 30.sp,
        modifier = Modifier.padding(16.dp)
    )
}


@Composable
fun SudokuGrid(
    field: Array<Array<Int>>,
    selection: Pair<Int, Int>?,
    vm: GameViewModel
) {
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

                                // add a highlight to the column + row the selected field is in

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
fun SudokuGridX(field: Array<Array<Int>>, selection: Pair<Int, Int>?, vm: GameViewModel) {
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
                                    when {
                                        i == j || i == gridSize - j - 1 -> this.background(Color.Blue) // Change the background to blue when i == j or i == gridSize - j - 1
                                        selection?.first == i && selection.second == j -> this.background(Color(0xFFffe0b3))
                                        else -> this
                                    }
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
fun SudokuGridEven(
    field: Array<Array<Int>>,
    selection: Pair<Int, Int>?,
    vm: GameViewModel,
    difficulty: Int) {
    with(ComposeStyles.SudokuGridStyles) {

        val completeField = field //delete after adding completeField: Array<Array<Int>> to input

        val gridSize = field.size
        val subGridSize = sqrt(gridSize.toDouble()).toInt()
        val gridWidth = ((boxSize.value + normalBorderWidth.value) * gridSize + thickBorderWidth.value * subGridSize).dp

        val highlightThreshold = when (difficulty) {
            1 -> 1.0
            2 -> 0.7
            3 -> 0.5
            else -> throw IllegalArgumentException("Mode must be 1, 2, or 3")
        }

        for (i in field.indices) {
            if (i % subGridSize == 0) GridDivider(thickBorderWidth, gridWidth)
            Row() {
                for (j in field[i].indices) {
                    if (j % subGridSize == 0) {
                        GridDivider(boxSize, thickBorderWidth)
                    }
                    (field[i][j] <= 0).let { fillAble ->
                        val randomValue = remember { Random.nextDouble() }
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .border(
                                    width = normalBorderWidth,
                                    color = Color.Black,
                                ).run {
                                    if (fillAble) this.clickable { vm.selectField(i, j) } else this
                                }.run {
                                    when {
                                        completeField[i][j] % 2 == 0 && randomValue <= highlightThreshold -> this.background(
                                            Color.Green
                                        ) // Change the background to green when the number in completeField is even and a random number is below the threshold
                                        selection?.first == i && selection.second == j -> this.background(
                                            Color(
                                                0xFFffe0b3
                                            )
                                        )

                                        else -> this
                                    }
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
 * The function initializes a timer that starts from zero and counts upwards.
 * The timer updates every second and the displayed time is formatted in
 * the "MM:SS" format. The timer runs indefinitely.
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

