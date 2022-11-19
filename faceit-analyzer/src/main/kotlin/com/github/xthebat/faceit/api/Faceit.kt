@file:Suppress("NOTHING_TO_INLINE")

package com.github.xthebat.faceit.api

import com.github.xthebat.faceit.api.records.Player
import com.github.xthebat.faceit.api.records.Response
import com.github.xthebat.faceit.api.records.ResponseResult
import com.github.xthebat.faceit.extensions.Rest
import java.util.*

class Faceit(baseUrl: String = "https://api.faceit.com") {

    companion object {
        const val USER_V1_ENDPOINT = "users/v1"
    }

    val rest = Rest(baseUrl)

    fun playerByNickname(nickname: String): Player {
        val response = rest.get<Response<Player>>("$USER_V1_ENDPOINT/nicknames/$nickname")
        check(response.result == ResponseResult.OK) { "Faceit response result is not OK: ${response.result}" }
        return response.payload
    }

    fun playerById(id: UUID): Player {
        val response = rest.get<Response<Player>>("$USER_V1_ENDPOINT/users/$id")
        check(response.result == ResponseResult.OK) { "Faceit response result is not OK: ${response.result}" }
        return response.payload
    }

//    fun playerMatchesStats(player: Player, game: String, page: Int, size: Int) {
//        val response = rest.get<Response<Player>>("stats/time/users/${player.id}/games/{$game}?page={page}&size={size}")
//    }

    inline fun playerByNicknameOrNull(nickname: String) = runCatching { playerByNickname(nickname) }.getOrNull()

    inline fun playerByIdOrNull(id: UUID) = runCatching { playerById(id) }.getOrNull()
}