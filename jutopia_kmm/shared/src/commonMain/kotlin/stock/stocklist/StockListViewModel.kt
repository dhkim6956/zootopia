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
                    val randomChange = Random.nextDouble(-10.0, 10.0)
                    val newPrice = it.price + randomChange
                    val changAmount = newPrice - it.price
                    val changePercent = ((newPrice - it.price) / it.price) * 100
                    it.copy(price = newPrice, changePercent = changePercent, changeAmount = changAmount)
                }
                _stocks.emit(updatedStocks)
                delay(10000) // 30 seconds
            }
        }
    }

    private fun initialData() = listOf(
        Stock("AAPL", 150.0, 0.0, true,0.0),
        Stock("GOOGL", 2500.0, 0.0, false,0.0),
        Stock("AMZN", 3300.0, 0.0, true,0.0),
        Stock("MSFT", 200.0, 0.0, true,0.0),
        Stock("TSLA", 600.0, 0.0, false,0.0),
        Stock("dasd", 600.0, 0.0, false,0.0),
    )
}