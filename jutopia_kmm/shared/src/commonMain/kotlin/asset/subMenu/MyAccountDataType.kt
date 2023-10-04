package asset.subMenu

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

enum class transactionType {
    Deposit,
    Withdrawal
}

data class depositDetail(val date: String, val time: String, val type: transactionType, val memo: String, val amount: Number, val changes: Number )

data class AccountInformation(val uuid: String, val bank: String, val number: String, val balance: Double)

@Serializable
data class AccountInfo(
    @SerialName("id")
    val uuid: String,
    @SerialName("account_name")
    val bank: String,
    @SerialName("account_number")
    val number: String,
    @SerialName("account_balance")
    val balance: Double
)

@Serializable
data class AccountResponseData(
    @SerialName("data")
    val body: AccountInfo
)