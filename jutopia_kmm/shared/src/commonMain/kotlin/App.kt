import androidx.compose.animation.EnterTransition
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import asset.Asset
import home.Home
import lease.LeaseScreen
import menus.Menus
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.RouteBuilder
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import news.News
import org.jetbrains.compose.resources.ExperimentalResourceApi
import school.School

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    val navigator = rememberNavigator()
    MaterialTheme {
        NavHost(
            navigator = navigator,
            navTransition = NavTransition(createTransition = EnterTransition.None),
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
            scene(
                route = "/lease"
            ) {
                LeaseScreen(navigator)
            }
        }
    }
}

expect fun getPlatformName(): String

expect val icehimchanFontFamily: FontFamily
expect val icejaramFontFamily: FontFamily
expect val icesiminFontFamily: FontFamily
expect val icesotongFontFamily: FontFamily
expect val pretendardFontFamily: FontFamily