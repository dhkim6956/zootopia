package asset.subMenu

import kotlinx.coroutines.flow.MutableStateFlow
import moe.tlaster.precompose.viewmodel.ViewModel

class MyStockViewModel: ViewModel() {
    private val _ownedStock: MutableStateFlow<List<StockDetail>> = MutableStateFlow(listOf(
        StockDetail(name = "현대차", bought = 191100, current = 191100, qty = 1, rate = 0.0, changes = Comparison.NotChanged),
        StockDetail(name = "삼성전자", bought = 68400, current = 68400, qty = 2, rate = 0.0, changes = Comparison.NotChanged),
        StockDetail(name = "에스엠", bought = 128300, current = 128300, qty = 1, rate = 0.0, changes = Comparison.NotChanged),
        StockDetail(name = "NAVER", bought = 201500, current = 201500, qty = 1, rate = 0.0, changes = Comparison.NotChanged),
        StockDetail(name = "한화", bought = 23950, current = 23950, qty = 4, rate = 0.0, changes = Comparison.NotChanged)
    ))

    val ownedStock: MutableStateFlow<List<StockDetail>> = _ownedStock
}