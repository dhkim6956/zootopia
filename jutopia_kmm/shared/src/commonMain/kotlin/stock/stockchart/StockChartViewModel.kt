package stock.stockchart

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import kotlin.random.Random

class StockChartViewModel(stockId: String) : ViewModel() {
    private val _chartData = MutableStateFlow<List<Pair<Int, Double>>>(listOf())
    val chartData: StateFlow<List<Pair<Int, Double>>> = _chartData
    var currentTime = 0
    private val dataQueue= ArrayDeque<Pair<Int, Double>>(60)
//    private val _stock = MutableLiveData<Stock>()
//    val stock: LiveData<Stock> get() = _stock
//
//    init {
//        // 서버나 데이터베이스에서 Stock 객체 불러오는 로직
//        _stock.value = fetchStockById(stockId) // 예시 함수
//    }
    init {
        viewModelScope.launch {
            while(true) {
                val newData = fetchNewDataForStock(stockId)
                currentTime += 1
                dataQueue.add(Pair(currentTime, newData))

                // 큐에 데이터가 60개 이상이면 맨 처음 데이터를 제거합니다.
                if (dataQueue.size > 10) {
                    dataQueue.removeFirst()
                }

                _chartData.emit(dataQueue.toList())
                delay(10000)
            }
        }
    }

    private fun fetchNewDataForStock(stockId: String): Double {
        // Dummy data generation logic depending on the stockId
        return when (stockId) {
            "AAPL" -> Random.nextDouble(140.0, 160.0)
            "GOOGL" -> Random.nextDouble(2000.0, 2500.0)
            "MSFT" -> Random.nextDouble(250.0, 300.0)
            else -> Random.nextDouble(50.0, 100.0)
        }
    }
}