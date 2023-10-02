package stock.stockchart

import co.touchlab.kermit.Logger
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecoder
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import stock.common.StockChart
import stock.common.StockResponse
import kotlin.random.Random
private val log = Logger.withTag("stockChart")

class StockChartViewModel(stockId: String, stockCode: String) : ViewModel() {
    private val _chartData = MutableStateFlow<List<Pair<String, Double>>>(listOf())
    val chartData: StateFlow<List<Pair<String, Double>>> = _chartData

    private val _isLoading = MutableStateFlow<Boolean>(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val apiService = StockChartApiService()
//    private val _stock = MutableLiveData<Stock>()
//    val stock: LiveData<Stock> get() = _stock
//
//    init {
//        // 서버나 데이터베이스에서 Stock 객체 불러오는 로직
//        _stock.value = fetchStockById(stockId) // 예시 함수
//    }
    init {
        viewModelScope.launch {
            _isLoading.emit(true)
            log.i { "주식 코드 : $stockCode" }
            val res = apiService.getStockChart(stockCode,TimeFrame.minute)
            val jsonRes = Json.decodeFromString<StockChart>(res.bodyAsText())
            val priceMapData = jsonRes.price

            val convertedData = priceMapData.mapNotNull { (key, value) ->
                value?.toDoubleOrNull()?.let {
                    Pair(key, it)
                }
            }
            _chartData.emit(convertedData)
            log.i{"변환 데이터 : $_chartData"}
            _isLoading.emit(false)

//            while(true) {
//                val newData = fetchNewDataForStock(stockId)
//                currentTime += 1
//                dataQueue.add(Pair(currentTime, newData))
//
//                // 큐에 데이터가 60개 이상이면 맨 처음 데이터를 제거합니다.
//                if (dataQueue.size > 10) {
//                    dataQueue.removeFirst()
//                }
//
//                _chartData.emit(dataQueue.toList())
//                delay(10000)
//            }
        }
    }

}