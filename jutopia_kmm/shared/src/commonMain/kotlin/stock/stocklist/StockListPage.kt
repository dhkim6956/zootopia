package stock.stocklist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.touchlab.kermit.Logger
import formatDouble
import moe.tlaster.precompose.navigation.Navigator
private val log = Logger.withTag("StockListPage")
@Composable
fun StockListPage(
    viewModel: StockListViewModel = moe.tlaster.precompose.viewmodel.viewModel(modelClass = StockListViewModel::class) {
        StockListViewModel()
    }, modifier: Modifier = Modifier,
    navigator: Navigator
) {
    val stocks by viewModel.stocks.collectAsState();
    var showOwnedOnly by remember { mutableStateOf(false) }
    val filteredStocks = if (showOwnedOnly) stocks.filter { it.isOwnedByUser } else stocks

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Text("보유 주식만 보기", fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 11.dp))
        Switch(checked = showOwnedOnly, onCheckedChange = { showOwnedOnly = it })
    }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(filteredStocks) { stock ->
            StockRow(stock) {
                //페이지 이동 로직
                log.i { stock.id }
                navigator.navigate("/stockChart/${stock.id}")
                log.i { "이동 실패" }
                
            }
            Divider()
        }
    }

}

@Composable
fun Divider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.Gray)
    )
}

@Composable
fun StockRow(stock: Stock, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(stock.name, fontSize = 20.sp)
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("${formatDouble(stock.price, 3)}", fontSize = 20.sp)
                val arrowAndColor =
                    if (stock.changePercent > 0) "↑" to Color.Red else "↓" to Color.Blue
                Text(arrowAndColor.first, color = arrowAndColor.second, fontSize = 20.sp)
                Text(
                    "${formatDouble(stock.changePercent, 3)}%",
                    color = arrowAndColor.second,
                    fontSize = 20.sp
                )
            }
            val changeColor = if (stock.changeAmount > 0) Color.Red else Color.Blue
            val displayChangeAmount =
                if (stock.changeAmount < 0) -stock.changeAmount else stock.changeAmount
            Text("${formatDouble(displayChangeAmount, 3)}", color = changeColor, fontSize = 14.sp)
        }
    }
}


