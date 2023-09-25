package stock.chart

import co.touchlab.kermit.Logger
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import stock.stocklist.Stock
import stock.stocklist.StockListViewModel
import kotlin.random.Random
private val log = Logger.withTag("StockDetailViewModel")

data class ChartDataPoint(
    val label: String,
    val price: Double
)

class StockDetailViewModel: ViewModel() {
    private val _chartData = MutableStateFlow(initialChartData())
    val chartData: StateFlow<List<ChartDataPoint>> = _chartData

    init {
        viewModelScope.launch {
            while (true) {
                val lastData = _chartData.value.last()
                val newPrice = lastData.price + Random.nextDouble(-10.0, 10.0)
                val newPoint = ChartDataPoint("New Minute", newPrice)

                val updatedData = chartData.value.drop(1) + newPoint // Drop oldest, add newest

                _chartData.emit(updatedData)
                delay(60000) // 1 minute
            }
        }
    }

    private fun initialChartData(): List<ChartDataPoint> {
        return List(60) {index ->
            ChartDataPoint("Minutes $index", 100.0 + Random.nextDouble(-10.0, 10.0))
        }
    }
}