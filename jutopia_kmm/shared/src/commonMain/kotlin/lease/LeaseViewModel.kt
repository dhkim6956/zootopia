package lease



import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope



class LeaseViewModel : ViewModel() {
    private val _selectedSeat = MutableStateFlow<Seat?>(null)

    val selectedSeat: StateFlow<Seat?> = _selectedSeat

    private val _seats = MutableStateFlow(List(20) { Seat(it, "자리 정보 $it", price = 1000, it % 2 == 0) })

    val seats: StateFlow<List<Seat>> = _seats


    fun reserveSeat(id: Int) {
        viewModelScope.launch {
            val updatedSeats = _seats.value.map {
                if (it.id == id) it.copy(isAvailable = false)
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