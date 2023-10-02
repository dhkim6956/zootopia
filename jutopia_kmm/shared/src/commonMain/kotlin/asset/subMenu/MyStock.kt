package asset.subMenu

import Variables.ColorsOnPrimary
import Variables.ColorsPrimary
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import moe.tlaster.precompose.viewmodel.viewModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun MyStock(viewModel: MyStockViewModel = viewModel(modelClass = MyStockViewModel::class) {
    MyStockViewModel()
}) {
    StockInfo()
    StockTable(viewModel)
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun StockInfo() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(108.dp)
            .background(ColorsPrimary)
            .padding(12.dp)
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource("drawable/coin_rabbit.png"),
                    null,
                    modifier = Modifier.width(24.dp)
                )
                Text("삼다수 증권", color = ColorsOnPrimary.copy(alpha = 0.74F))
            }
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text("총 손익", color = ColorsOnPrimary, fontSize = 24.sp)
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .width(160.dp)
                ) {
                    Text("-8100P", color = ColorsOnPrimary, fontSize = 24.sp)
                    Text("-2.13%", color = ColorsOnPrimary)
                }
            }
            Row (
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text("총 매입", color = ColorsOnPrimary.copy(alpha = 0.74F))
                    Text("380800P", color = ColorsOnPrimary.copy(alpha = 0.74F))
                }

                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text("총 평가", color = ColorsOnPrimary.copy(alpha = 0.74F))
                    Text("380800P", color = ColorsOnPrimary.copy(alpha = 0.74F))
                }
            }
        }
    }
}

@Composable
fun StockTable(viewModel: MyStockViewModel)
{
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TableHeader()
        TableItem("삼성", "67,000", "5,800", "4.32", Comparison.Increased)
        TableItem("삼성", "67,000", "5,800", "4.32", Comparison.Decreased)
        TableItem("삼성", "67,000", "5,800", "4.32", Comparison.Increased)
    }
}

@Composable
fun TableHeader() {
    Row {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(color = Color(0xFFEDEDED))
                .weight(1f)
        ) {
            Text("종목명")
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(color = Color(0xFFEDEDED))
                .weight(1f)
        ) {
            Text("매입가")
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(color = Color(0xFFEDEDED))
                .weight(1f)
        ) {
            Text("평가손익")
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(color = Color(0xFFEDEDED))
                .weight(1f)
        ) {
            Text("수익률")
        }
    }
}

@Composable
fun TableItem(stockName: String, bought: String, rate: String, percentage: String, state: Comparison) {

    val isIncreasing: Boolean = state == Comparison.Increased

    Row {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1f)
                .border(width = 0.5.dp, color = Color.LightGray)
                .height(40.dp)
                .padding(4.dp)
        ) {
            Text(stockName, overflow = TextOverflow.Ellipsis)
        }
        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier
                .weight(1f)
                .border(width = 0.5.dp, color = Color.LightGray)
                .height(40.dp)
                .padding(4.dp)
        ) {
            Text(bought, overflow = TextOverflow.Ellipsis)
        }
        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier
                .weight(1f)
                .border(width = 0.5.dp, color = Color.LightGray)
                .height(40.dp)
                .padding(4.dp)
        ) {
            Text(rate, overflow = TextOverflow.Ellipsis, color = if(isIncreasing) Color.Red else Color.Blue)
        }
        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier
                .weight(1f)
                .border(width = 0.5.dp, color = Color.LightGray)
                .height(40.dp)
                .padding(4.dp)
        ) {
            Text("$percentage%", overflow = TextOverflow.Ellipsis, color = if(isIncreasing) Color.Red else Color.Blue)
        }
    }
}