package stock.common

import BottomTabBar
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import common.TopPageBar
import moe.tlaster.precompose.navigation.BackHandler
import moe.tlaster.precompose.navigation.Navigator
import stock.stockchart.StockChartPage
import stock.stocklist.StockListViewModel
import stock.stocktrade.StockTradePage


@Composable
fun StockScreen(
    stockId: String,
    navigator: Navigator,
    viewModel: StockViewModel = moe.tlaster.precompose.viewmodel.viewModel(modelClass = StockViewModel::class) {
        StockViewModel()
    }
) {
    val stocks by StockListViewModel().stocks.collectAsState();
    val stock = stocks.firstOrNull { it.id == stockId }
    var currentPage = viewModel.currentPage

    CustomBackHandler(onBack = {}, navigator = navigator, viewModel)

    Column(
        modifier = Modifier.padding(bottom = 80.dp).fillMaxHeight()
    ) {
        TopPageBar("주식", navigator)
        StockRow(stock!!, {})
        Column {
            when (currentPage.value) {
                PageType.CHART -> {
                    StockChartPage(
                        stockId,
                        navigator = navigator,
                    )
                }

                PageType.TRADE -> {
                    StockTradePage(stockId, navigator = navigator)
                }
            }

        }
    }
    BottomTabBar(navigator)
}

@Composable
fun CustomBackHandler(
    onBack: () -> Unit,
    navigator: Navigator,
    viewModel: StockViewModel
) {
    val currentOnBackPressed by rememberUpdatedState(onBack)
    var currentPage = viewModel.currentPage
    BackHandler {
        when (currentPage.value) {
            PageType.TRADE -> {
                viewModel.changePage(PageType.CHART)
            }

            else -> {
                currentOnBackPressed()
                navigator.goBack() // 원래 뒤로가기 동작
            }
        }
    }
}
