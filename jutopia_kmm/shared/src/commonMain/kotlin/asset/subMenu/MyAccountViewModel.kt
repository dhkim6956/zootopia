package asset.subMenu

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class MyAccountViewModel: ViewModel() {
    private val _transactionHistory: MutableList<depositDetail> = mutableListOf(
        depositDetail("2023.09.07", "15:14:00", transactionType.Withdrawal, "포인트 환전", 110000, 60000),
        depositDetail("2023.09.01", "09:00:00", transactionType.Withdrawal, "임대료", 80000, 170000),
        depositDetail("2023.09.01", "00:00:00", transactionType.Deposit, "월 기본급", 200000, 250000)
    )

    val transactionHistory: List<depositDetail> = _transactionHistory

    private val _accountInformation: MutableStateFlow<AccountInformation?> = MutableStateFlow(null)

    val accountInformation: StateFlow<AccountInformation?> = _accountInformation

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)

    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            _accountInformation.emit(MyAccountAPI().getAccountInfo())
            _isLoading.emit(false)
        }
    }
}