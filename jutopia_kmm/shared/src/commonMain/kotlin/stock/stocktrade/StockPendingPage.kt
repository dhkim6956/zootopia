package stock.stocktrade

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import stock.stocklist.Stock

@Composable
fun StockPendingPage(
    stock: Stock,
    viewModel: StockTradeViewModel = moe.tlaster.precompose.viewmodel.viewModel(modelClass = StockTradeViewModel::class) {
        StockTradeViewModel()
    }
) {
    val myStocks = viewModel.myStocks.collectAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        item {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("구분", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text("주문가", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text("주문수량", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
        }

        items(myStocks.value) { stockTriple ->
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
//                Text(
//                    text = stockTriple.first.type.toString(),
//                    fontSize = 18.sp,
//                    textAlign = TextAlign.Start
//                )
//                Text(
//                    text = stockTriple.second.toString(),
//                    fontSize = 18.sp,
//                    textAlign = TextAlign.Center
//                )
//                Text(
//                    text = stockTriple.third.toString(),
//                    fontSize = 18.sp,
//                    textAlign = TextAlign.End
//                )
            }
        }
    }
}