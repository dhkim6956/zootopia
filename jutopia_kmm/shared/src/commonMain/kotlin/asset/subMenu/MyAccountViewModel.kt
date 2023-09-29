package asset.subMenu

import moe.tlaster.precompose.viewmodel.ViewModel

class MyAccountViewModel: ViewModel() {
    private val _transactionHistory: MutableList<depositDetail> = mutableListOf(
        depositDetail("2023.09.07", "15:14:00", transactionType.Withdrawal, "포인트 환전", 110000, 60000),
        depositDetail("2023.09.01", "09:00:00", transactionType.Withdrawal, "임대료", 80000, 170000),
        depositDetail("2023.09.01", "00:00:00", transactionType.Deposit, "월 기본급", 200000, 250000)
    )

    val transactionHistory: List<depositDetail> = _transactionHistory
}