package view.game

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
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

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.withStyle

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun GameView(vm: GameViewModel) {
    val gameUiState by vm.uiState.collectAsState()
    var popupControl by remember { mutableStateOf(false) }

    MaterialTheme {
        Column(
            modifier = Modifier
                .padding(top = 50.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) { //title
                Text("Sudoku", modifier = Modifier.onClick { vm.updatePoints(1000) })
            }
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column (
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp) // Padding from the center
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center
                ){
                    SudokuGrid(gameUiState.selection, vm)

                }

                Column (
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp) // Padding from the center
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Box(
                        modifier = Modifier.align(Alignment.Start)
                            .width(120.dp)
                            .height(50.dp)
                            .padding(start = 50.dp)

                    ){
                        Text(CountUpTimer(vm),
                            modifier = Modifier,
                            color = Color.Black,
                            fontSize = 20.sp
                        )
                    }

                    Box(
                        modifier = Modifier.align(Alignment.Start)
                           // .width(120.dp)
                            .align(Alignment.CenterHorizontally)
                            .padding(10.dp, 10.dp)
                    ){
                        Text("Achievable Points:",
                            modifier = Modifier,
                            color = Color.Black,
                            fontSize = 15.sp
                        )
                        Text(EarningPoints(vm).toString(),
                            modifier = Modifier.padding(top = 20.dp),
                            color = Color.Black,
                            fontSize = 20.sp
                        )
                    }

                    Button(
                        modifier = Modifier.align(Alignment.Start)
                            .width(140.dp)
                            .align(Alignment.CenterHorizontally)
                            .padding(15.dp, 5.dp)
                       ,
                        onClick = { vm.getHint() }, enabled = gameUiState.points >= hintCosts,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
                    ) {
                        Text(
                            if (!gameUiState.win) "Hint" else "You won!",
                            color = if (!gameUiState.win) Color.Unspecified else Color.Blue
                        )
                    }

                    Button(
                        onClick = {
                            vm.submit()
                        }, modifier = Modifier
                            .align(Alignment.Start)
                            .width(140.dp)
                            .align(Alignment.CenterHorizontally)
                            .padding(15.dp, 5.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)

                    ) {
                        Text(if (vm.uiState.value.win) "Close" else "Submit")
                    }


                    Button(
                        onClick = {
                            vm.endGame()
                        }, modifier = Modifier.align(Alignment.Start)
                            .width(140.dp)
                            .align(Alignment.CenterHorizontally)
                            .padding(15.dp, 5.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)

                    ) {
                        Text("EndGame",
                            color = Color(0xFF4552B8)
                        )

                    }
                }
            }
        }
    }
}

@Composable
fun EarningPoints(vm: GameViewModel): Int {
    LaunchedEffect(key1 = true) {
        while (!vm.uiState.value.win) {
            delay(60000L)
            vm.updatePoints(-5)
        }
    }

    return vm.uiState.value.points
}


@Composable
fun SudokuGrid(
    selection: Pair<Int, Int>?,
    vm: GameViewModel
) {
    var random by remember { mutableStateOf(vm.getRandomSeed()) }

    with(ComposeStyles.SudokuGridStyles) {

        val state = vm.uiState.value
        val field = state.field
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
                            modifier = Modifier // add a highlight to the column + row the selected field is in
                                .size(30.dp)
                                .border(
                                    width = normalBorderWidth,
                                    color = Color.DarkGray,
                                ).run {
                                    if (fillAble) this.clickable { vm.selectField(i, j) } else this
                                }.run {
                                    val selected = selection?.first == i && selection.second == j
                                    val backgroundColor = when (state.mode?.name) {
                                        "Even-Odd" -> {
                                            if (vm.uiState.value.fieldComplete[i][j] % 2 == 0 && random[i][j] >= state.mode.selection.difficulty.selected.ordinal
                                            )
                                                if (selected) Color(0xFF80B4F1) else Color(0xFF9AC1EF)
                                            else
                                                if (selected) Color(0xFFE4E4E4) else Color.White
                                        }

                                        "X-Sudoku" -> {
                                            if (i == j || i == gridSize - j - 1)
                                                if (selected) Color(0xFF97DA4D) else Color(0xFFA7DD6B)
                                            else
                                                if (selected) Color(0xFFE4E4E4) else Color.White
                                        }

                                        else -> {
                                            if (selected) Color(0xFFE4E4E4) else Color.White
                                        }
                                    }
                                    this.background(color = backgroundColor)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                    fontWeight = if (!fillAble) FontWeight.SemiBold else FontWeight.Normal,
                                    color = if (!fillAble) Color.Black else Color.DarkGray,
                                    text = if (field[i][j] == 0) "" else field[i][j].absoluteValue.toString(),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .height(textSize)
                                        .fillMaxSize()


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
fun GridDivider(heightDp: Dp, widthDp: Dp) {
    Divider(
        modifier = Modifier
            .height(heightDp)
            .background(ComposeStyles.SudokuGridStyles.borderColor)
            .width(widthDp)
    )
}

@Composable
fun CountUpTimer(vm: GameViewModel): String {
    var timePassed by remember { mutableStateOf(0) }
    val timerText = remember {
        derivedStateOf {
            String.format("%02d:%02d", timePassed / 60, timePassed % 60)
        }
    }

    LaunchedEffect(key1 = true) {
        while (!vm.uiState.value.win) {
            delay(1000L)
            timePassed++
        }
    }

    return timerText.value

}

