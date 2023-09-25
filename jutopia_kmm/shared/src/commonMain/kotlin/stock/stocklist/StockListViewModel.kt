package stock.stocklist



import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import kotlin.random.Random


class StockListViewModel : ViewModel() {
    private val _stocks = MutableStateFlow(initialData())
    val stocks: StateFlow<List<Stock>> = _stocks

    init {
        viewModelScope.launch {
            while (true) {
                val updatedStocks = stocks.value.map {
                    val randomChange = Random.nextDouble(-1.0, 1.0)
                    val newPrice = it.price + randomChange
                    val changePercent = ((newPrice - it.price) / it.price) * 100
                    it.copy(price = newPrice, changePercent = changePercent)
                }
                _stocks.emit(updatedStocks)
                delay(10000) // 30 seconds
            }
        }
    }

    private fun initialData() = listOf(
        Stock("AAPL", 150.0, 0.0, true),
        Stock("GOOGL", 2500.0, 0.0, false),
        Stock("AMZN", 3300.0, 0.0, true),
        Stock("MSFT", 200.0, 0.0, true),
        Stock("TSLA", 600.0, 0.0, false),
        Stock("dasd", 600.0, 0.0, false),
    )
}