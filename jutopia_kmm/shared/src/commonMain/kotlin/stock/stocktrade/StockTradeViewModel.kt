package stock.stocktrade

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import stock.common.PageType
import stock.stocklist.Stock
import kotlin.random.Random

enum class TradeType {
    Buy,
    SELL,
    Pending
}
enum class StockStatus {
    Contract,
    NonTrading,
}


class StockTradeViewModel() : ViewModel() {

    private val _myStocks = MutableStateFlow<List<Triple<Stock,Double, Int>>>(listOf())
    val myStocks: StateFlow<List<Triple<Stock,Double, Int>>> = _myStocks

    private val _tradeQuantity = MutableStateFlow(0.0)
    var tradeQuantity: StateFlow<Double> =  _tradeQuantity

    private val _tradePrice = MutableStateFlow<Double>(0.0)
    var tradePrice: StateFlow<Double> = _tradePrice

    private val _tradeType = MutableStateFlow<TradeType>(TradeType.Buy)
    var tradeType : StateFlow<TradeType> = _tradeType

    fun changeTradeType(tradeType: TradeType){
        _tradeType.value = tradeType
    }

    fun tradeStock(stock:Stock, price: Double, quantity: Int ){
        runBlocking {
            val currentStocks = _myStocks.first().toMutableList()
            val newStockEntry = Triple(stock, price, quantity)

            currentStocks.add(newStockEntry)
            _myStocks.emit(currentStocks)
        }
    }

}