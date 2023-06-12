package view.menu

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.DIFFICULT
import domain.GameMode
import domain.SIZES

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


            Row (horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()){
                for (i in gameUiState.gameModes) {
                    gameMode(i, gameUiState.selectedMode, vm)
                }
            }
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Text("")
            }
            Row (horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Card(shape = RoundedCornerShape(20.dp), elevation = 10.dp){
                gameUiState.buttonState.let { points ->
                    Button(
                        onClick = {
                            vm.buttonPressed(points)
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = if (points > 0) Color.Green else Color.Red)
                    ) {
                        if (gameUiState.buttonState > 0) {
                            Text(
                                buildAnnotatedString {
                                    append("Verdienen \n")
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
                                    append("Kaufen \n")
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
    val modifier = Modifier
        .padding(15.dp)

    Card(
        modifier = Modifier
            .border(2.dp, if (type == selected) Color.Black else if(type.available) Color.Green else Color.Red, shape = RoundedCornerShape(20.dp))
            .padding(15.dp),
        elevation = 10.dp,
        //shape = RoundedCornerShape(20.dp),
        onClick = { vm.updateGameMode(type)}


    ) {
        Column() {
            Row() {
                Image(
                painter = painterResource("drawable/test.jpg"),
                contentDescription = "Andy Rubin",
                     contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .clip(RoundedCornerShape(10))
                    .width(128.dp)
            )
            }
            Row() {
                Box() {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(type.name)
                        if (type.available) {
                        } else {
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

    Column() {

        Row {
            for (i in SIZES.values()) {
                Chip(
                    onClick = { vm.updateSize(type, i) },
                    modifier = Modifier
                        .width(90.dp)
                        .border(0.dp, if (type.options.size.selected == i) Color.Black else if(i.ordinal <= type.options.size.available.ordinal) Color.Black else Color.Red),
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = i.toString(),
                                style = TextStyle(fontSize= 12.sp)
                            )


                            if (i.ordinal <= type.options.size.available.ordinal) {

                            } else {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                }
            }
        }
        Row {
            for (i in DIFFICULT.values()) {
                Chip(
                    onClick = { vm.updateDifficult(type, i) },
                    modifier = Modifier
                        .width(90.dp)
                        .border(0.dp, if (type.options.difficult.selected == i) Color.Black else if(i.ordinal <= type.options.difficult.available.ordinal) Color.Green else Color.Red),
                ) {
                    Box() {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = i.toString(),
                                style = TextStyle(fontSize = 12.sp)
                            )
                            if (i.ordinal <= type.options.difficult.available.ordinal) {

                            } else {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}