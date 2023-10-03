package stock.stockchart

import co.touchlab.kermit.Logger
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import kotlin.random.Random

private val log = Logger.withTag("stockChart")

class StockChartViewModel(stockId: String, stockCode: String) : ViewModel() {
    private val _chartData = MutableStateFlow<List<Pair<String, Double>>>(listOf())
    val chartData: StateFlow<List<Pair<String, Double>>> = _chartData

    private val _isLoading = MutableStateFlow<Boolean>(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val apiService = StockChartApiService()

//    init {
//        viewModelScope.launch {
//            _isLoading.emit(true)
//            log.i { "주식 코드 : $stockCode" }
//            val res = apiService.getStockChart(stockCode,TimeFrame.minute)
//            val jsonRes = Json.decodeFromString<StockChart>(res.bodyAsText())
//            val priceMapData = jsonRes.price
//            log.i { "가격 데이터 : $priceMapData" }
//
//            val convertedData = priceMapData.mapNotNull { (key, value) ->
//                value?.replace(",", "")?.toDoubleOrNull()?.let {
//                    Pair(key, it)
//                }
//            }
//            log.i{"변환 데이터 : $convertedData"}
//
//            _chartData.emit(convertedData)
//            log.i{"데이터 : ${_chartData.value}"}
//            _isLoading.emit(false)
//
//        }
//    }

    init {
        viewModelScope.launch {
            _isLoading.emit(false)

            var currentTime = 0
            while (true) {
                val randomPrice =
                    Random.nextDouble(100.0, 200.0)
                currentTime += 1


                val currentData = _chartData.value


                val updatedData = if (currentData.size >= 60) {
                    currentData.drop(1).toMutableList()
                } else {
                    currentData.toMutableList()
                }

                updatedData.add(Pair(currentTime.toString(), randomPrice))

                _chartData.emit(updatedData)

                delay(5000)
            }

        }
    }
}

