package view.menu

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun MenuView(vm: MenuViewModel) {
    val gameUiState by vm.uiState.collectAsState()
    val modifier: Modifier = Modifier.padding(1.dp)

    MaterialTheme {
        Column {
            Text(
                buildAnnotatedString {
                    append("Points: ")
                    withStyle(
                        style = SpanStyle(fontWeight = FontWeight.W900, color = Color(0xFF4552B8))
                    ) {
                        append(gameUiState.xp.toString())
                    }
                }, modifier = Modifier.clickable { vm.updateXP(1000) }
            )
            Row {
                for (i in gameUiState.gameModes) {
                    gameMode(i, gameUiState.selectedMode, vm)
                }
            }

            gameUiState.buttonState.let { points ->
                Button(onClick = {
                    vm.buttonPressed(points)
                }, colors = ButtonDefaults.buttonColors(backgroundColor = if(points > 0) Color.Green else Color.Red)) {
                    if(gameUiState.buttonState > 0){
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
                    }else{
                        Text(
                            buildAnnotatedString {
                                append("Kaufen \n")
                                withStyle(
                                    style = SpanStyle(fontWeight = FontWeight.W900, color = Color(0xFF4552B8))
                                ) {
                                    append((points*-1).toString())
                                }
                            }
                        )
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
        modifier = modifier.border(2.dp, if (type == selected) Color.Black else if(type.available) Color.Green else Color.Red),
        elevation = 10.dp,
        onClick = { vm.updateGameMode(type) }
    ) {
        Column {
            Image(
                painter = painterResource("drawable/test.jpg"),
                contentDescription = "Andy Rubin",
                modifier = Modifier
                    .clip(RoundedCornerShape(10))
                    .width(128.dp)
            )
            Text(type.name)
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
                        .border(2.dp, if (type.options.size.selected == i) Color.Black else if(i.ordinal <= type.options.size.available.ordinal) Color.Green else Color.Red),
                ) {
                    Text(i.toString())
                }
            }
        }
        Row {
            for (i in DIFFICULT.values()) {
                Chip(
                    onClick = { vm.updateDifficult(type, i) },
                    modifier = Modifier
                        .border(2.dp, if (type.options.difficult.selected == i) Color.Black else if(i.ordinal <= type.options.difficult.available.ordinal) Color.Green else Color.Red),
                ) {
                    Text(i.toString())
                }
            }
        }
    }
}