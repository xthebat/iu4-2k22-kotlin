package sem09

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File

val defaultMappers = List(16) {
    ObjectMapper().apply {
        configure(JsonParser.Feature.ALLOW_COMMENTS, true)
        configure(JsonParser.Feature.ALLOW_TRAILING_COMMA, true)
        configure(SerializationFeature.INDENT_OUTPUT, true)
        registerKotlinModule()
    }
}


inline fun <reified T> String.fromJson(mapper: ObjectMapper = sem08.defaultMappers.random()): T =
    mapper.readValue(this)


inline fun <reified T> File.fromJson(mapper: ObjectMapper = sem08.defaultMappers.random()): T =
    mapper.readValue(this)