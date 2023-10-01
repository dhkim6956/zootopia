package stock.stockchart

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.aay.compose.lineChart.LineChart
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineType
import moe.tlaster.precompose.navigation.Navigator
import stock.common.Divider
import stock.common.PageType
import stock.common.StockViewModel
import stock.stocklist.StockListViewModel

@Composable
fun StockChartPage(
    stockId: String,
    viewModel: StockChartViewModel = moe.tlaster.precompose.viewmodel.viewModel(modelClass = StockChartViewModel::class) {
        StockChartViewModel(stockId)
    },
    stockViewModel: StockViewModel = moe.tlaster.precompose.viewmodel.viewModel(modelClass = StockViewModel::class) {
        StockViewModel(stockId)
    },
    navigator: Navigator,
    modifier: Modifier = Modifier
) {
    val chartData by viewModel.chartData.collectAsState()
    val clickedPoints = remember { mutableStateListOf<Pair<Float, Float>>() }
    Box(
        modifier = Modifier.height(350.dp)
    ){
        chart(
            chartData, clickedPoints,
        )
    }
    Button(
        onClick = {
            stockViewModel.changePage(PageType.TRADE)
        },
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Text("주문")
    }


}

@Composable
fun chart(
    chartData: List<Pair<Int, Double>>,
    clickedPoints: MutableList<Pair<Float, Float>>,
    modifier: Modifier = Modifier
) {
    val latestData = chartData.takeLast(10)
    val lineParameters: List<LineParameters> = listOf(
        LineParameters(
            label = "Stock Price",
            data = latestData.map { it.second },
            lineColor = Color.Gray,
            lineType = LineType.CURVED_LINE,
            lineShadow = true,
        )

    )

    val xAxisData = chartData.map { (it.first.toString()) }


    Box {
        LineChart(
            modifier = Modifier
                .fillMaxWidth(),
            linesParameters = lineParameters,
            xAxisData = xAxisData,

            )
        DisplayClickedPointInfo(clickedPoints)
    }

}

@Composable
fun DisplayClickedPointInfo(clickedPoints: List<Pair<Float, Float>>) {
    if (clickedPoints.isNotEmpty()) {
        val lastPoint = clickedPoints.last()
        val (x, y) = lastPoint

        Text("Clicked Point: x = $x, y = $y")
    }
}