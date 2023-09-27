package stock.common

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import moe.tlaster.precompose.viewmodel.ViewModel


enum class PageType {
    CHART,
    TRADE
}

class StockViewModel :ViewModel() {
    private val _currentPage = MutableStateFlow(PageType.CHART)
    val currentPage: StateFlow<PageType> = _currentPage

    fun changePage(newPage: PageType) {
        _currentPage.value = newPage
    }
}