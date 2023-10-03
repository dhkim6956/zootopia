package school

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class SchoolViewModel: ViewModel() {
    private val _notice: MutableStateFlow<List<NotiDetail>> = MutableStateFlow(listOf())

    val notice: StateFlow<List<NotiDetail>> = _notice

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)

    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            _notice.emit(SchoolAPI().getNoti())
            _isLoading.emit(false)
        }
    }
}