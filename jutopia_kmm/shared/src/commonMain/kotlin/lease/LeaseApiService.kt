package lease

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.json.Json
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

private val log = Logger.withTag("LeaseAPI")

class LeaseApiService {

    private companion object {
        const val BASE_URL = "http://j9c108.p.ssafy.io:8000/rent-server/seat-service/api/seat"
    }
    private val client = HttpClient(CIO) {
        install(ContentNegotiation){
            json(
                kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
            )
        }
        defaultRequest {
            url(BASE_URL)
        }
    }
    private fun HttpRequestBuilder.apiUrl(path: String){
        url("$BASE_URL/$path")
    }

    suspend fun getAllSeats(school: String, grade: Int, clazzNumber: Int): HttpResponse {
        return client.get{
            apiUrl("seats")
            url.parameters.append("school", school)
            url.parameters.append("grade", grade.toString())
            url.parameters.append("clazzNumber", clazzNumber.toString())
            log.i { "$url" }
        }
    }

    suspend fun getSeat(seatId: String): HttpResponse{
        return client.get {
            apiUrl("seats/$seatId")
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun setSeat(seatId: String): HttpResponse{
        val userId = "0e1f7fba-ca6a-4ba9-82f8-da4dec778e74"
        val seatRequest = SeatRequest(seatId, userId)
        val jsonData = Json.encodeToString(seatRequest);

        return client.put{
            apiUrl("seats/$seatId")
            header("Content-Type", ContentType.Application.Json.toString())
            body = jsonData

        }
    }
}