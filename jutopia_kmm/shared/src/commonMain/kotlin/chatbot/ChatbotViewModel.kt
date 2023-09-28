package chatbot


import co.touchlab.kermit.Logger
import io.ktor.client.call.body
import io.ktor.client.statement.bodyAsChannel
import io.ktor.client.statement.bodyAsText
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

}