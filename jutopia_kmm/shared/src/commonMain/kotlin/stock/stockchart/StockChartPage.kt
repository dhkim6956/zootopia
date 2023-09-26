package stock.stockchart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.aay.compose.lineChart.LineChart
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineType

@Composable
fun StockChartPage(stockId: String,
    viewModel: StockChartViewModel = moe.tlaster.precompose.viewmodel.viewModel(modelClass = StockChartViewModel::class) {
        StockChartViewModel(stockId)
    }
) {
    val chartData by viewModel.chartData.collectAsState()
    chart(chartData)
}

@Composable
fun chart(chartData: List<Pair<Int, Double>>) {
    val lineParameters: List<LineParameters> = listOf(
        LineParameters(
            label = "Stock Price",
            data = chartData.map { it.second },
            lineColor = Color.Gray,
            lineType = LineType.CURVED_LINE,
            lineShadow = true,
        )

    )

    val xAxisData = chartData.map{(it.first.toString())}

    Box(Modifier) {
        LineChart(
            modifier = Modifier.fillMaxSize(),
            linesParameters = lineParameters,
            xAxisData = xAxisData
        )
    }
}