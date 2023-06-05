import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.flow.MutableStateFlow
import view.game.GameView
import view.game.GameViewModel
import view.menu.MenuView
import view.menu.MenuViewModel

@Composable
@Preview
fun App(navigation: MutableStateFlow<Int>, menuViewModel: MenuViewModel, gameViewMode: GameViewModel) {
    val navState = navigation.collectAsState()
    if(navState.value == Screens.GAME.ordinal){
        GameView(gameViewMode)
    }else{
        MenuView(menuViewModel)
    }
}
//iwas lustiges
//hallo alex
fun main(){
    val navigation = MutableStateFlow(Screens.MENU.ordinal)
    val menuViewModel = MenuViewModel(navigation)
    val gameViewMode = GameViewModel(navigation)
    application {
        Window(onCloseRequest = ::exitApplication) {
            App(navigation, menuViewModel, gameViewMode)
        }
    }
}

object NavController{

}
enum class Screens{MENU, GAME}
