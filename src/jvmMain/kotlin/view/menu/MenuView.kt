package view.menu

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.DIFFICULTIES
import domain.GameMode

@Composable
@Preview
fun MenuView(vm: MenuViewModel) {
    val gameUiState by vm.uiState.collectAsState()
    val modifier: Modifier = Modifier.padding(1.dp)

    MaterialTheme {
        Column {
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Text("Welcome to Sudoku!")
            }
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Text("")
            }
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Card(shape = RoundedCornerShape(20.dp), elevation = 10.dp) {
                    Text(
                        buildAnnotatedString {
                            append("Points: ")
                            withStyle(
                                style = SpanStyle(fontWeight = FontWeight.W900, color = Color(0xFF4552B8))
                            ) {
                                append(gameUiState.xp.toString())
                            }
                        }, modifier = Modifier.clickable { vm.updateXP(1000) }
                            .background(Color.LightGray)
                            .padding(10.dp)

                    )
                }
            }
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Text("")
            }


            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                for (i in gameUiState.gameModes) {
                    gameMode(i, gameUiState.selectedMode, vm)
                }
            }
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Text("")
            }
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Card(shape = RoundedCornerShape(20.dp), elevation = 10.dp) {
                    gameUiState.buttonState.let { points ->
                        Button(
                            onClick = {
                                vm.buttonPressed()
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = if (points > 0) Color.Green else Color.Red)
                        ) {
                            if (gameUiState.buttonState > 0) {
                                Text(
                                    buildAnnotatedString {
                                        append("EARN \n")
                                        withStyle(
                                            style = SpanStyle(fontWeight = FontWeight.W900, color = Color(0xFF4552B8))
                                        ) {
                                            append(points.toString())
                                        }
                                    }
                                )
                            } else {
                                Text(
                                    buildAnnotatedString {
                                        append("SPENT \n")
                                        withStyle(
                                            style = SpanStyle(fontWeight = FontWeight.W900, color = Color(0xFF4552B8))
                                        ) {
                                            append((points * -1).toString())
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun gameMode(type: GameMode, selected: GameMode, vm: MenuViewModel) {

    Card(
        modifier = Modifier
            .padding(15.dp)
            .clip(RoundedCornerShape(10)),
        elevation = 10.dp,
        //shape = RoundedCornerShape(20.dp),
        onClick = { vm.updateGameMode(type) }


    ) {
        Column( horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource("drawable/"+type.name+".png"),
                    contentDescription = "Andy Rubin",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10))
                )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box() {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(type.name, modifier = Modifier.align(Alignment.CenterVertically))
                        if (!type.available) {
                            Icon(imageVector = Icons.Default.Lock, contentDescription = null)
                        }

                    }
                }
            }
            radioChips(type, vm)

        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun radioChips(type: GameMode, vm: MenuViewModel) {
    Column {
        Row {
            var selectedSize by remember { mutableStateOf(type.selection.size.selected) }
            for (i in type.availableSizes) {
                Box(
                    modifier = Modifier
                        .width(90.dp)
                        .clickable {
                            vm.updateSize(type, i)
                            selectedSize = i
                        }
                        .background(
                            color = if (selectedSize == i) Color.LightGray else Color.Transparent,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Box(
                        modifier = Modifier.padding(4.dp).clip(RoundedCornerShape(8.dp))
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = i.toString(),
                                color = if (type.selection.size.selected != i && i.ordinal > type.selection.size.available.ordinal) {
                                    Color.Red
                                }
                                else {
                                     Color.Black
                                },
                                style = TextStyle(fontSize = 12.sp),
                                textAlign = TextAlign.Center
                            )
                            /*if (type.selection.size.selected != i && i.ordinal > type.selection.size.available.ordinal) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        }*/
                        }
                    }
                }
            }
        }
        Row {
            var selectedDifficulty by remember { mutableStateOf(type.selection.difficulty.selected) }
            for (i in DIFFICULTIES.values()) {
                Box(
                    modifier = Modifier
                        .width(90.dp)
                        .clickable {
                            vm.updateDifficult(type, i)
                            selectedDifficulty = i
                        }
                        .background(
                            color = if (selectedDifficulty == i) Color.LightGray else Color.Transparent,
                            shape = RoundedCornerShape(8.dp) // Festlegen der abgerundeten Ecken
                        )

                ) {
                    Box(
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = i.toString(),
                                color = if (type.selection.difficulty.selected != i && i.ordinal > type.selection.difficulty.available.ordinal) {
                                    Color.Red
                                }
                                else {
                                    Color.Black
                                },
                                style = TextStyle(fontSize = 12.sp)
                            )
                            /*if (type.selection.difficulty.selected != i && i.ordinal > type.selection.difficulty.available.ordinal) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                            }*/
                        }
                    }
                }
            }
        }
    }
}
