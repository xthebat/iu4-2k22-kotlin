package com.github.xthebat.faceit

import com.github.xthebat.faceit.api.Faceit
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope


suspend fun main() = coroutineScope {
    val faceit = Faceit()
    val player = faceit.playerByNickname("20142014")

    println(player)

    val friends = player.friends
        .map { async { faceit.playerByIdOrNull(it) } }
        .awaitAll()
        .filterNotNull()

    friends
        .groupingBy { it.country }
        .eachCount()
        .map { (country, count) -> country to count }
        .sortedByDescending { it.second }
        .forEach { println(it) }
}