import androidx.compose.animation.EnterTransition
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import asset.Asset
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
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import news.News
import school.School
import stock.stockchart.StockChartScreen
import stock.stocklist.StockListScreen

private val log = Logger.withTag("App")

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
                route = "/asset/deposit"
            ) {
                Asset(navigator)
            }
            scene(
                route = "/asset/save"
            ) {
                Asset(navigator)
            }
            scene(
                route = "/asset/point"
            ) {
                Asset(navigator)
            }
            scene(
                route = "/asset/stock"
            ) {
                Asset(navigator)
            }
            scene(
                route = "/asset/building"
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
                route = "/send"
            ) {
                Send(navigator)
            }
            scene(
                route = "/save"
            ) {
                Save(navigator)
            }
            scene(
                route = "/send_detail"
            ) {
                Send_detail(navigator)
                route = "/stocklist"
            ){
                StockListScreen(navigator)
            }
            scene(
                route = "/stockChart/{stockId}?"
            ) {backStackEntry ->
                val stockId: String? = backStackEntry.path<String>("stockId")
                StockChartScreen(stockId!!, navigator)
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