package school

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class NotiDetail(val idx: Int, val title: String, val date: String, val time: String)

@Serializable
data class NotiItem(
    @SerialName("id")
    val idx: Int,
    @SerialName("title")
    val title: String,
    @SerialName("content")
    val content: String,
    @SerialName("view_count")
    val viewCnt: Int,
)

@Serializable
data class NotiResponse(
    @SerialName("data")
    val body: List<NotiItem>
)

@Serializable
data class NotiDetailResponse(
    @SerialName("data")
    val body: NotiItem
)