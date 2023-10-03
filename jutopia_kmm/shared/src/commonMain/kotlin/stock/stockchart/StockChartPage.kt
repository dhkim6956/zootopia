package stock.stockchart

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import com.aay.compose.baseComponents.model.GridOrientation
import com.aay.compose.lineChart.LineChart
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineType
import moe.tlaster.precompose.navigation.Navigator
import stock.common.PageType
import stock.common.StockViewModel

private val log = Logger.withTag("stockChart")

@Composable
fun StockChartPage(
    stockId: String,
    stockCode: String,
    viewModel: StockChartViewModel = moe.tlaster.precompose.viewmodel.viewModel(modelClass = StockChartViewModel::class) {
        StockChartViewModel(stockId, stockCode)
    },
    stockViewModel: StockViewModel = moe.tlaster.precompose.viewmodel.viewModel(modelClass = StockViewModel::class) {
        StockViewModel(stockId, stockCode)
    },
    navigator: Navigator,
    modifier: Modifier = Modifier
) {
    val chartData by viewModel.chartData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val clickedPoints = remember { mutableStateListOf<Pair<Float, Float>>() }

    if (isLoading) {
        CircularProgressIndicator()
    } else{
        Box(
            modifier = Modifier.height(350.dp)
        ){
            log.i { "${chartData.size}" }
//            chart(
//                chartData, clickedPoints,
//            )
            ScrollableLineChart(chartData, clickedPoints)


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


}

@Composable
fun chart(
    chartData: List<Pair<String, Double>>,
    clickedPoints: MutableList<Pair<Float, Float>>,
    modifier: Modifier = Modifier
) {
    val latestData = chartData.takeLast(24)

    val lineParameters: List<LineParameters> = listOf(
        LineParameters(
            label = "주식 가격",
            data = latestData.map { it.second },
            lineColor = Color.Gray,
            lineType = LineType.DEFAULT_LINE,
            lineShadow = false,
        )

    )

    val xAxisData = latestData.map { (it.first) }


    Box(Modifier.fillMaxSize()) {
        LineChart(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            linesParameters = lineParameters,
            isGrid = true,
            xAxisData = xAxisData,
            showGridWithSpacer = true,
            yAxisRange = 10,
            oneLineChart = false,
            gridOrientation = GridOrientation.HORIZONTAL,
            animateChart = true,
            )
        DisplayClickedPointInfo(clickedPoints)
    }

}

@Composable
fun DisplayClickedPointInfo(clickedPoints: List<Pair<Float, Float>>) {
    if (clickedPoints.isNotEmpty()) {
        val lastPoint = clickedPoints.last()
        val (x, y) = lastPoint

        Text("시간 = $x, 가격 = $y")
    }
}

@Composable
fun ScrollableLineChart(chartData: List<Pair<String, Double>>
                        ,clickedPoints: MutableList<Pair<Float, Float>>) {
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        scrollState.scrollTo(scrollState.maxValue)
    }

    val lineParameters: List<LineParameters> = listOf(
        LineParameters(
            label = "주식 가격",
            data = chartData.map { it.second },
            lineColor = Color.Gray,
            lineType = LineType.DEFAULT_LINE,
            lineShadow = false
        )
    )

    val xAxisData = chartData.map { it.first }

    Box(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .width(((chartData.size / 4) * 300).dp)
            .fillMaxHeight()
    ) {
        LineChart(
            linesParameters = lineParameters,
            isGrid = true,
            xAxisData = xAxisData,
            showGridWithSpacer = true,
            yAxisRange = 10,
            oneLineChart = false,
            gridOrientation = GridOrientation.HORIZONTAL,
            animateChart = true
        )
        DisplayClickedPointInfo(clickedPoints)
    }
}

