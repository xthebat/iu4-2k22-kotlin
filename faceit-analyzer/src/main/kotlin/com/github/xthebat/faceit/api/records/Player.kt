package com.github.xthebat.faceit.api.records

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class Player(
    val id: UUID,
    val nickname: String,
    val country: String,
    val friends: List<UUID>,
) : ResponsePayload
