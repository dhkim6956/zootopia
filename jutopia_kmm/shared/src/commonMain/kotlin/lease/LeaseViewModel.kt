package lease



import co.touchlab.kermit.Logger
import io.ktor.client.call.body
import io.ktor.client.statement.bodyAsChannel
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
private val log = Logger.withTag("LeaseAPI")



class LeaseViewModel : ViewModel() {
    private val _selectedSeat = MutableStateFlow<Seat?>(null)

    val selectedSeat: StateFlow<Seat?> = _selectedSeat

    private val _seats = MutableStateFlow<List<Seat>>( listOf() )

    val seats: StateFlow<List<Seat>> = _seats

    private val apiService = LeaseApiService()
    init {
        viewModelScope.launch {
            try {
                val response = apiService.getAllSeats("ssafy", 1, 1)
                val apiResponse = Json.decodeFromString<ApiResponse>(response.bodyAsText())
                val seatList: List<Seat> = apiResponse.body
                _seats.emit(seatList)
            } catch (e:Exception){
                log.i {"연결실패, ${e}"}
            }
        }
    }


    fun reserveSeat(id: String) {
        viewModelScope.launch {
            val updatedSeats = _seats.value.map {
                if (it.id.equals(id)) it.copy(seatStatus = "INUSE")
                else it
            }
            _seats.emit(updatedSeats)
        }
    }

    fun selectSeat(seat: Seat) {
        viewModelScope.launch {
            println("Setting selectedSeat to $seat")
            _selectedSeat.emit(seat)
        }
    }

    fun clearSelectedSeat() {
        viewModelScope.launch {
            _selectedSeat.emit(null)
        }
    }
}