import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import asset.Asset
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import org.jetbrains.compose.resources.ExperimentalResourceApi
import home.Home
import menus.Menus
import news.News
import school.School

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    val navigator = rememberNavigator()
    MaterialTheme {
        NavHost(
            navigator = navigator,
            navTransition = NavTransition(),
            initialRoute = "/home",
        ) {
            scene(
                route = "/home"
            ) {
                Home(navigator)
            }
            scene(
                route = "/asset"
            ) {
                Asset(navigator)
            }
            scene(
                route = "/school"
            ) {
                School(navigator)
            }
            scene(
                route = "/news"
            ) {
                News(navigator)
            }
            scene(
                route = "/menus"
            ) {
                Menus(navigator)
            }
        }
    }
}



expect fun getPlatformName(): String