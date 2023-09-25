package stock.chart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import moe.tlaster.precompose.viewmodel.viewModel

private val log = Logger.withTag("StockChartPage")

@Composable
fun StockChartPage(
    viewModel: StockDetailViewModel = viewModel(modelClass = StockDetailViewModel::class) {
        StockDetailViewModel()
    }
) {
    val chartData by viewModel.chartData.collectAsState()
    Column {
        Text("name")
        DrawChart(chartData)
    }
}

@Composable
fun DrawChart(data: List<ChartDataPoint>) {
    val maxValue = data.maxOf { it.price }
    val minValue = data.minOf { it.price }

    val factor = 100 / (maxValue - minValue)

    Box(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val step = size.width / (data.size - 1)
            for (i in 1 until data.size) {
                val x1 = step * (i - 1)
                val y1 = size.height - ((data[i - 1].price - minValue) * factor).toFloat()
                val x2 = step * i
                val y2 = size.height - ((data[i].price - minValue) * factor).toFloat()

                drawLine(
                    color = Color.Blue,
                    start = Offset(x1, y1),
                    end = Offset(x2, y2),
                    strokeWidth = 3f,
                    cap = StrokeCap.Round
                )
            }
        }
    }
}
