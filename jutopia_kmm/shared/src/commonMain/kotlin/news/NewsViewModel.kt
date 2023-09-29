package news

import moe.tlaster.precompose.viewmodel.ViewModel

class NewsViewModel(): ViewModel() {
    private val _newses: List<NewsDetail> = mutableListOf(
        NewsDetail("삼성전자, 고화질 콘텐츠 위한 SSD", "뉴시스", "2023.09.07", "10:09", "https://www.naver.com"),
        NewsDetail("429만원 한정 수량 ... 삼성전자, '폴드5'", "더팩트", "2023.09.07", "10:00", "https://www.naver.com"),
        NewsDetail("EU'빅테크 규제법' 삼성만 제외됐다", "서울신문", "2023.09.07", "06:10", "https://www.naver.com"),
        NewsDetail("'쉿! 유출 안돼' 삼성, 반도체 개발에 ...", "중앙일보", "2023.09.07", "05:04", "https://www.naver.com"),
    )

    val newses: List<NewsDetail> = _newses
}