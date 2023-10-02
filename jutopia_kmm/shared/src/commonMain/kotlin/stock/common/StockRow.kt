package stock.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import formatDouble
import stock.stocklist.Stock

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
        Text(stock.stockName, fontSize = 20.sp)
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("${stock.price}", fontSize = 20.sp)
                val arrowAndColor =
                    if (stock.changePercent!! > 0) "↑" to Color.Red else "↓" to Color.Blue
                Text(arrowAndColor.first, color = arrowAndColor.second, fontSize = 20.sp)
                Text(
                    "${formatDouble(stock.changePercent!!, 3)}%",
                    color = arrowAndColor.second,
                    fontSize = 20.sp
                )
            }
            val changeColor = if (stock.changeAmount!! > 0) Color.Red else Color.Blue
            val displayChangeAmount =
                if (stock.changeAmount!! < 0) -stock.changeAmount!! else stock.changeAmount
            Text("${formatDouble(displayChangeAmount!!, 3)}", color = changeColor, fontSize = 14.sp)
        }
    }
}