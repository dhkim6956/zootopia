package asset.subMenu

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.StateFlow
import moe.tlaster.precompose.viewmodel.ViewModel

class MyAccountViewModel: ViewModel() {
    private val _transactionHistory = mutableStateOf(mutableListOf<depositDetail>(
        depositDetail("2023.09.07", "15:14:00", transactionType.Withdrawal, "포인트 환전", 110000, 60000),
        depositDetail("2023.09.01", "09:00:00", transactionType.Withdrawal, "임대료", 80000, 170000),
        depositDetail("2023.09.01", "00:00:00", transactionType.Deposit, "월 기본급", 200000, 250000)
    ))


    val transactionHistory: State<List<depositDetail>> = _transactionHistory

}