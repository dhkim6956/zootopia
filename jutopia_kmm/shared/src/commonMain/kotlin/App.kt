import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import asset.Asset
import chatbot.ChatbotScreen
import com.svenjacobs.reveal.RevealCanvas
import com.svenjacobs.reveal.rememberRevealCanvasState
import home.Bank
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import home.Home
import home.Market
import home.Notice
import home.Rent
import home.Save
import home.Send
import home.Send_detail
import home.Stock
import home.Trade
import lease.LeaseScreen
import menus.Menus
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.transition.NavTransition
import news.News
import school.NotiContents
import school.School
import stock.common.StockScreen
import stock.stockchart.StockChartScreen
import stock.stocklist.StockListScreen


@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    val revealCanvasState = rememberRevealCanvasState()
    val navigator = rememberNavigator()
    MaterialTheme {
        RevealCanvas(
            modifier = Modifier.fillMaxSize(),
            revealCanvasState = revealCanvasState,
        ) {
            NavHost(
                navigator = navigator,
                navTransition = NavTransition(
                    createTransition = EnterTransition.None,
                    destroyTransition = ExitTransition.None,
                    pauseTransition = ExitTransition.None,
                    resumeTransition = EnterTransition.None
                ),
                initialRoute = "/home",
            ) {
                scene(
                    route = "/home"
                ) {
                    Home(navigator)
                }
                scene(
                    route = "/asset/{category}?"
                ) {
                    val category: Int? = it.path<Int>("category")
                    Asset(navigator, category)
                }
                scene(
                    route = "/school"
                ) {
                    School(navigator)
                }
                scene(
                    route = "/notice/{idx}?"
                ) {
                    val idx: Int? = it.path<Int>("idx")
                    NotiContents(navigator, idx!!)
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
                    route = "/bank"
                ) {
                    Bank(navigator)
                }
                scene(
                    route = "/stock"
                ) {
                    Stock(navigator)
                }
                scene(
                    route = "/rent"
                ) {
                    Rent(navigator)
                }
                scene(
                    route = "/trade"
                ) {
                    Trade(navigator)
                }
                scene(
                    route = "/market"
                ) {
                    Market(navigator)
                }
                scene(
                    route = "/notice"
                ) {
                    Notice(navigator)
                }
                scene(
                    route = "/lease"
                ) {
                    LeaseScreen(navigator)
                }
                scene(
                    route = "/stocklist"
                ) {
                    StockListScreen(navigator)
                }
                scene(
                    route = "/stockChart/{stockId}?"
                ) { backStackEntry ->
                    val stockId: String? = backStackEntry.path<String>("stockId")
                    StockScreen(stockId!!, navigator)
                }
                scene(
                    route = "/stocktrade/{stockId}?"
                ) { backStackEntry ->
                    val stockId: String? = backStackEntry.path<String>("stockId")
                    StockScreen(stockId!!, navigator)
                }
                scene(
                    route = "/send"
                ) {
                    Send(navigator)
                }
                scene(
                    route = "/save"
                ) {
                    Save(navigator, revealCanvasState = revealCanvasState)
                }
                scene(
                    route = "/send_detail"
                ) {
                    Send_detail(navigator)
                }
                scene(
                    route = "/chatbot"
                ){
                    ChatbotScreen(navigator)
                }
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
expect fun formatDouble(value: Double, decimalPlaces: Int): String
expect fun addComma(value: Double): String

expect fun openUrl(url: String?)