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
import stock.common.StockApiService
import kotlin.random.Random

private val log = Logger.withTag("StockAPI")

class StockListViewModel : ViewModel() {
    private val _stocks = MutableStateFlow<List<Stock>>(listOf())

    val stocks: StateFlow<List<Stock>> = _stocks

    private val stockApiService = StockApiService();

    init {
        viewModelScope.launch {
            //TODO: 1분마다 데이터 새로 받아오도록 변경
            try {
                val response = stockApiService.getAllStocks();
                val apiResponse = Json.decodeFromString<StockListResponse>(response.bodyAsText())
                val stockList = apiResponse.body
                _stocks.emit(stockList!!)
                while (true) {
                    val updatedStocks = stocks.value.map {
                        val randomChange = Random.nextDouble(-10.0, 10.0)
                        val newPrice = it.price + randomChange
                        val changAmount = newPrice - it.price
                        val changePercent = ((newPrice - it.price) / it.price) * 100
                        it.copy(
                            price = newPrice.toInt(),
                            changePercent = changePercent,
                            changeAmount = changAmount
                        )
                    }
                    _stocks.emit(updatedStocks)
                    delay(10000)
                }
            } catch (e: Exception) {
                log.i { "주식 리스트 에러: ${e}" }
            }
        }
    }
}