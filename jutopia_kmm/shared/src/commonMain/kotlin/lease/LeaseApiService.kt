package lease

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.json.Json
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json

class LeaseApiService {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation){
            json(
                kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
            )
        }
    }

    suspend fun getAllSeats(school: String, grade: Int, clazzNumber: Int): HttpResponse {
        return client.get("http://10.0.2.2:8080/api/seats?school=$school&grade=$grade&clazzNumber=$clazzNumber")
    }
}