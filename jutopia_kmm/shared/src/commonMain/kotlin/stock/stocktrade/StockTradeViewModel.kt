package stock.stocktrade

import co.touchlab.kermit.Logger
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import stock.common.StockApiService
import stock.common.MyStock
import stock.common.MyStocksResponse
import stock.common.StockRequest
import stock.common.StockTradeResponse

enum class TradeType {
    BUY,
    SELL,
    Pending
}
enum class StockStatus {
    Contract,
    NonTrading,
}

private val log = Logger.withTag("StockAPI")

class StockTradeViewModel() : ViewModel() {
    private val memberId = "d79eb207-290c-4d6c-9a1e-41dd4e831692"

    private val _myStocks = MutableStateFlow<List<MyStock>>(listOf())
    val myStocks: StateFlow<List<MyStock>> = _myStocks

    private val _tradeQuantity = MutableStateFlow(0.0)
    var tradeQuantity: StateFlow<Double> =  _tradeQuantity

    private val _tradePrice = MutableStateFlow<Double>(0.0)
    var tradePrice: StateFlow<Double> = _tradePrice

    private val _tradeType = MutableStateFlow<TradeType>(TradeType.BUY)
    var tradeType : StateFlow<TradeType> = _tradeType

    private val _myStocksCount = MutableStateFlow<List<Pair<String, Int>>>(listOf())
    var myStocksCount: StateFlow<List<Pair<String, Int>>> = _myStocksCount

    private val stockApiService: StockApiService = StockApiService()

    init {
        getMyStockInfo()
    }

    private fun getMyStockInfo(){
        //TODO: 현재 모든 주식목록을 불러와 낭비가 있다. 특정 유저의 특정 주식의 정보만 불러올 수 있도록 API 제작 요청 예정
        viewModelScope.launch {
            try {
                val res = stockApiService.getMyAllStocks(memberId)
                val apiResponse = Json.decodeFromString<MyStocksResponse>(res.bodyAsText())
                _myStocks.emit(apiResponse.body)
                log.i { "내 주식 목록: ${myStocks.value}" }
                val stockCountPairs = apiResponse.body.map { myStock ->
                    Pair(myStock.stockId, myStock.qty)
                }
                _myStocksCount.emit(stockCountPairs)
            } catch (e: Exception){
                log.i{"나의 주식 목록 초기화 실패 : ${e}"}
            }
        }
    }


    fun changeTradeType(tradeType: TradeType){
        _tradeType.value = tradeType
    }


    fun tradeStock(stockRequest: StockRequest) {
        viewModelScope.launch {
            try {
                val response = stockApiService.tradeStock(stockRequest)
                val apiResponse = Json.decodeFromString<StockTradeResponse>(response.bodyAsText())
                log.i {
                    "트레이드 결과: ${apiResponse.body}"
                }
                getMyStockInfo()
            } catch (e: Exception) {
                log.i{"trade 에러 : ${e}"}
            }
        }
    }


}