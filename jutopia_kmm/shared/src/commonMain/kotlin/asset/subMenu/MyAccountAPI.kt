package asset.subMenu

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class MyAccountAPI {
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
    suspend fun getAccountInfo(studentId:String ): AccountInformation {


        try {
            val response: AccountResponseData = httpClient.get("http://j9c108.p.ssafy.io:8000/member-server/api/account") {
                url {
                    parameters.append("studentId", studentId)
                }
            }.body<AccountResponseData>()

            Logger.d("${response.body.bank}")

            val transform: AccountInformation = AccountInformation(response.body.uuid, response.body.bank, response.body.number, response.body.balance)

            Logger.d("fetch data!!!")

            return transform

        } catch (e: Exception) {
            println("Error: ${e.message}")
            return AccountInformation("error", "", "", 0.0)
        } finally {
            httpClient.close()
        }
    }
}