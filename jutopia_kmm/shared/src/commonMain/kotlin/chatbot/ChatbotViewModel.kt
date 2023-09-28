package chatbot


import co.touchlab.kermit.Logger
import co.touchlab.kermit.Message
import io.ktor.client.call.body
import io.ktor.client.statement.bodyAsChannel
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import lease.LeaseApiService
import lease.ListResponse
import lease.Seat
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
private val log = Logger.withTag("ChatbotView")

sealed class ApiResponse {

}

class ChatbotViewModel : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()
    private val _isSending = MutableStateFlow(false)
    val isSending: StateFlow<Boolean> = _isSending.asStateFlow()

    private val serverDummyReplies = listOf(
        "안녕하세요, 어떻게 도와드릴까요?",
        "네, 알겠습니다.",
        "죄송합니다, 다시 한번 말씀해주실 수 있나요?"
    )
    //Message를 받아서 넣어준다
    fun addMessage(message: ChatMessage) {
        _messages.value = _messages.value + message
    }

    suspend fun addAndRespond(message: String) {
        _isSending.value = true
        addMessage(ChatMessage("newId", message, "newItem", false))

        delay(10000) //가상의 딜레이

        val reply = serverDummyReplies.random()
        addMessage(ChatMessage("server", reply, "newTime", true))

        _isSending.value = false
    }

    init {
        val dummyData = listOf(
            ChatMessage("1", "안녕하세요, 무엇을 도와드릴까요?", "12:01", true),
        )
        _messages.value = dummyData
    }
}