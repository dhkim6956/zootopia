package stock.common

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import lease.SeatRequest
import stock.stocklist.StockRequest

private val log = Logger.withTag("StockAPI")
class StockApiService {

    private companion object {
        const val BASE_URL = "http://j9c108.p.ssafy.io:8000/stock-server/api"
    }
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json { ignoreUnknownKeys = true }
            )
        }
        defaultRequest {
            url(BASE_URL)
        }
    }
    private fun HttpRequestBuilder.apiUrl(path: String){
        url("$BASE_URL/$path")
    }

    suspend fun getAllStocks(): HttpResponse{
        return client.get{
            apiUrl("stock/")
        }
    }

    suspend fun getStock(stockId: String): HttpResponse {
        return client.get{
            apiUrl("stock/$stockId")
        }
    }

    suspend fun getMyAllStocks(memberId: String): HttpResponse {
        return client.get{
            apiUrl("memberstock/$memberId")
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun tradeStock(stockRequest: StockRequest): HttpResponse {
        val jsonData = Json.encodeToString(stockRequest)
        return client.post {
            apiUrl("trade/")
            header("Content-Type", ContentType.Application.Json.toString())
            body = jsonData
        }
    }





}