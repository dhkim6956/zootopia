package school

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import moe.tlaster.precompose.viewmodel.ViewModel

class SchoolViewModel: ViewModel() {
    private val _notice = mutableStateOf(mutableListOf<NotiDetail>(
        NotiDetail(0, "칠판당번 공지","2023.09.07", "10:09:00"),
        NotiDetail(1, "청소구역 바꼈습니다~~","2023.09.01", "10:00:00"),
        NotiDetail(2, "포인트 규칙 수정사항","2023.09.01", "09:35:00"),
        NotiDetail(3, "환율 주의사항입니다","2023.08.21", "17:12:00")
    ))

    val notice: State<List<NotiDetail>> = _notice
}