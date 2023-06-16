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
                Box(modifier = Modifier.padding(20.dp)) {
                    Text("Welcome to Sudoku!")
                }
            }
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                for (i in gameUiState.gameModes) {
                    gameMode(i, gameUiState.selectedMode, vm)
                }
            }
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                Card(shape = RoundedCornerShape(20.dp), elevation = 10.dp, modifier = Modifier.width(120.dp).height(60.dp)) {
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
                                        append("SPEND \n")
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
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth().padding(10.dp)){
                Card(shape = RoundedCornerShape(20.dp), elevation = 10.dp, modifier = Modifier.width(120.dp).height(60.dp)) {
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
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun gameMode(type: GameMode, selected: GameMode, vm: MenuViewModel) {

    Card(
        modifier = Modifier
            .padding(30.dp)
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
                        if (!type.available) {
                            Icon(imageVector = Icons.Default.Lock, contentDescription = null)
                        }
                        Text(type.name, modifier = Modifier.align(Alignment.CenterVertically))

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
        Row(modifier = Modifier.padding(10.dp)){
            var selectedSize by remember { mutableStateOf(type.selection.size.selected) }
            for (i in type.availableSizes) {
                Button(
                    onClick = {
                        vm.updateSize(type, i)
                        selectedSize = i
                    },
                    modifier = Modifier
                        .width(110.dp)
                        .height(30.dp)
                        .padding(end = 8.dp), // Abstand am rechten Rand des Buttons
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (selectedSize == i) Color.LightGray else Color.White,
                        contentColor = if (selectedSize == i) Color.Black else Color.Gray
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    if (type.selection.size.selected != i && i.ordinal > type.selection.size.available.ordinal) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Text(text = i.toString(), style = TextStyle(fontSize = 11.sp))
                }

            }
        }
        Row (modifier = Modifier.padding(10.dp)){
            var selectedDifficulty by remember { mutableStateOf(type.selection.difficulty.selected) }
            for (i in DIFFICULTIES.values()) {
                Button(
                    onClick = {
                        vm.updateDifficult(type, i)
                        selectedDifficulty = i
                    },
                    modifier = Modifier
                        .width(110.dp)
                        .height(30.dp)
                        .padding(end = 8.dp), // Abstand am rechten Rand des Buttons
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (selectedDifficulty == i) Color.LightGray else Color.White,
                        contentColor = if (selectedDifficulty == i) Color.Black else Color.Gray
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    if (type.selection.difficulty.selected != i && i.ordinal > type.selection.difficulty.available.ordinal) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Text(text = i.toString(), style = TextStyle(fontSize = 11.sp))
                }

            }
        }
    }
}
