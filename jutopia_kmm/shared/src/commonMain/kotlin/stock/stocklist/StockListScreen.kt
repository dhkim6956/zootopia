package stock.stocklist

import BottomTabBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import common.TopPageBar
import lease.LeasePage
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun StockListScreen(navigator: Navigator) {
    Column(modifier = Modifier.fillMaxHeight().padding(bottom = 100.dp)) {
        TopPageBar("주식", navigator=navigator)
        StockListPage(navigator = navigator)
    }
    BottomTabBar(navigator)
}
