package school

import asset.subMenu.Comparison
import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class SchoolAPI {
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
    suspend fun getNoti(): List<NotiDetail> {


        try {
            val response: List<NotiItem> = httpClient.get("http://j9c108.p.ssafy.io:8000/member-server/api/notice").body<NotiResponse>().body

            val transform: List<NotiDetail> = response.map { noti ->
                NotiDetail(noti.idx, noti.title, "2023.09.07", "10:00:00")
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

    @Throws(Exception::class)
    suspend fun getNotiDetail(id: Int): NoticeDetail {


        try {
            val response: NotiItem = httpClient.get("http://j9c108.p.ssafy.io:8000/member-server/api/notice/$id").body<NotiDetailResponse>().body

            val transform: NoticeDetail = NoticeDetail(response.title, response.content, "2023.09.07", "10:00:00", response.viewCnt)

            Logger.d("fetch data!!!")

            return transform
        } catch (e: Exception) {
            println("Error: ${e.message}")
            return NoticeDetail("ERROR", "API연결실패", "2023.09.07", "10:00:00", 0)
        } finally {
            httpClient.close()
        }
    }
}