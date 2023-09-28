package lease

import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.JsonTransformingSerializer

//data class Seat(val id: Int, val info: String, val price: Int, val isAvailable: Boolean)


@Serializable
data class ApiResponse(
    val result: ApiResult,
    val body: List<Seat>
)

@Serializable
data class ApiResult(
    val result_code: Int,
    val result_message: String,
    val result_description: String
)
@Serializable
data class Seat(
    val id: String,
    val position: Int,
    val price: Double,
    val userId: String?,
    val seatStatus: String,
    val clazzNumber: Int,
    val grade: Int,
    val school: String,
)