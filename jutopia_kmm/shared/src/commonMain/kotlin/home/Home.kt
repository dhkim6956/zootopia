@file:OptIn(ExperimentalResourceApi::class)

package home

import BottomTabBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import common.TopPageBar
import lease.LeasePage
import lease.LeaseScreen
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import stock.stocklist.Divider
import stock.stocklist.StockListPage
import stock.stocklist.StockListScreen


//@Composable
//fun Home(navigator: Navigator) {
//    Column {
//        TopPageBar("홈")
//        BottomTabBar(navigator)
//    }
//}

@Composable
fun Home(navigator: Navigator) {
    Column(modifier = Modifier.padding(bottom = 56.dp)) {
        TopPageBar("홈")
        StockListPage(
            modifier = Modifier.weight(1f)
        )
    }
    BottomTabBar(navigator)
}