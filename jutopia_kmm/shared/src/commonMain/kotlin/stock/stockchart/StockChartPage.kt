package stock.stockchart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.aay.compose.barChart.BarChart
import com.aay.compose.barChart.model.BarParameters
import com.aay.compose.baseComponents.model.GridOrientation
import com.aay.compose.donutChart.PieChart
import com.aay.compose.donutChart.model.PieChartData
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
//            chart(
//                chartData, clickedPoints,
//            )
            BarChartSample(chartData)
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
    val latestData = chartData.takeLast(15)

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
            modifier = Modifier
                .border(2.dp, color = Color.Magenta),
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
fun BarChartSample(chartData: List<Pair<String, Double>>) {

    val testBarParameters: List<BarParameters> = listOf(
        BarParameters(
            dataName = "주식 가격",
            data = chartData.map { it.second },
            barColor = Color(0xFF6C3428)
        ),
    )

    Box(Modifier.fillMaxSize().padding(bottom = 5.dp)) {
        BarChart(
            chartParameters = testBarParameters,
            gridColor = Color.DarkGray,
            xAxisData = chartData.map{it.first},
            isShowGrid = true,
            animateChart = true,
            showGridWithSpacer = true,
            yAxisStyle = TextStyle(
                fontSize = 14.sp,
                color = Color.DarkGray,
            ),
            xAxisStyle = TextStyle(
                fontSize = 14.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.W400
            ),
            yAxisRange = 15,
            barWidth = 13.dp,
        )
    }
}