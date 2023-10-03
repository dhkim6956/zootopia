package news

import asset.subMenu.Comparison
import asset.subMenu.ResponseData
import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class NewsAPI {
    private  val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    @Throws(Exception::class)
    suspend fun getNewses(brand: String): List<NewsDetail> {


        try {
            val response: ResponseNewsData = httpClient.get("http://j9c108.p.ssafy.io:8000/news-server/naver/${brand}/1/20").body<ResponseNewsData>()

            val monthToNum = mapOf<String, String>("Jan" to "01", "Feb" to "02", "Mar" to "03", "Apr" to "04", "May" to "05", "Jun" to "06", "Jul" to "07", "Aug" to "08", "Sep" to "09", "Oct" to "10", "Nov" to "11", "Dec" to "12")

            val transform: List<NewsDetail> = response.body.map { news ->
                val year = news.pubDate.slice(IntRange(12,15))
                val month = monthToNum[news.pubDate.slice(IntRange(8,10))]
                val date = news.pubDate.slice(IntRange(5,6))
                val time = news.pubDate.slice(IntRange(17,24))

                Logger.d("year is $year")
                Logger.d("month is $month")
                Logger.d("date is $date")
                Logger.d("time is $time")

                NewsDetail(news.title, news.description, "네이버 뉴스", "$year.$month.$date", time, news.link)
            }

            Logger.d("fetch data!!!")

            return transform

        } catch (e: Exception) {
            println("Error: ${e.message}")
            return emptyList()
        } finally {
            httpClient.close()
        }
    }
}