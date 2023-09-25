package stock.stocklist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.dp

@Composable
fun StockListPage(
    viewModel: StockListViewModel = moe.tlaster.precompose.viewmodel.viewModel(modelClass = StockListViewModel::class) {
        StockListViewModel()
    }, modifier: Modifier = Modifier
) {
    val stocks by viewModel.stocks.collectAsState();
    var showOwnedOnly by remember { mutableStateOf(false) }
    val filteredStocks = if (showOwnedOnly) stocks.filter { it.isOwnedByUser } else stocks

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Show Owned Stocks Only")
        Switch(checked = showOwnedOnly, onCheckedChange = { showOwnedOnly = it })
    }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(filteredStocks) { stock ->
                StockRow(stock)
                Divider()
        }
    }

}

@Composable
fun Divider(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.Gray)
    )
}

@Composable
fun StockRow(stock: Stock) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(stock.name)
        Row {
            Text("${stock.price}")
            val arrowAndColor = if (stock.changePercent > 0) "↑" to Color.Red else "↓" to Color.Blue
            Text(arrowAndColor.first, color = arrowAndColor.second)
            Text("${stock.changePercent}%", color = arrowAndColor.second)
        }
    }
}