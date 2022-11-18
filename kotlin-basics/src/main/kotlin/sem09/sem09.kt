package sem09

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import kong.unirest.Unirest


//typealias Response = Map<String, Any>

typealias Json = Map<String, Any>


enum class Result { OK }

@JsonIgnoreProperties(ignoreUnknown = true)
data class Payload(
    val id: String,
    val friends: List<String>
)


data class Response(
    val result: Result,
    val payload: Payload
)

fun main() {
    val request = Unirest.get("https://api.faceit.com/users/v1/nicknames/20142014")
    val response = request.asString()
    check(response.status == 200) { "Request failed: $request" }
    val body = response.body.fromJson<Response>()
    check(body.result == Result.OK) { "Response result is not ok: ${body.result}" }

    body.payload.friends.forEach {

    }

    println(body)
}