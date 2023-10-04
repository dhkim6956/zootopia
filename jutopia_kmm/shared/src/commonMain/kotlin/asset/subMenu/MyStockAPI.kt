package asset.subMenu

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class MyStockAPI {
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
    suspend fun getMyStock(): List<StockDetail> {


        try {
            val response: ResponseData = httpClient.get("http://j9c108.p.ssafy.io:8000/stock-server/api/memberstock/7a7d8d91-b63a-42da-9552-62c749d9ae94").body<ResponseData>()

            val transform: List<StockDetail> = response.body.map { si ->
                StockDetail(si.stockName, si.prevMoney, si.nowMoney, si.qty, si.changeRate, if(si.type == 1) Comparison.Increased else if (si.type == 0) Comparison.NotChanged else Comparison.Decreased)
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