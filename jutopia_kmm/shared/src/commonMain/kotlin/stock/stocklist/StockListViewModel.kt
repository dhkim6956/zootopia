package stock.stocklist


import co.touchlab.kermit.Logger
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import stock.common.Stock
import stock.common.StockApiService
import stock.common.StockListResponse
import kotlin.random.Random

private val log = Logger.withTag("StockAPI")

class StockListViewModel : ViewModel() {
    private val _stocks = MutableStateFlow<List<Stock>>(listOf())

    val stocks: StateFlow<List<Stock>> = _stocks

    private val stockApiService = StockApiService();

    init {
        viewModelScope.launch {
            try {
                while (true) {
                    val response = stockApiService.getAllStocks();
                    val apiResponse = Json.decodeFromString<StockListResponse>(response.bodyAsText())
                    val stockList = apiResponse.body
                    _stocks.emit(stockList!!)
                    delay(60000)
                }
            } catch (e: Exception) {
                log.i { "주식 리스트 에러: ${e}" }
            }
        }
    }
}