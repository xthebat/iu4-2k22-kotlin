package sem08

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File

typealias Header = Map<String, Int>
//typealias Localization = Map<String, String>


val defaultMappers = List(16) {
    ObjectMapper().apply {
        configure(JsonParser.Feature.ALLOW_COMMENTS, true)
        configure(JsonParser.Feature.ALLOW_TRAILING_COMMA, true)
        configure(SerializationFeature.INDENT_OUTPUT, true)
        registerKotlinModule()
    }
}


inline fun <reified T> String.fromJson(mapper: ObjectMapper = defaultMappers.random()): T =
    mapper.readValue(this)


inline fun <reified T> File.fromJson(mapper: ObjectMapper = defaultMappers.random()): T =
    mapper.readValue(this)


fun String.toTableHeader(): Header = split(",")
    .mapIndexed { index, name -> name.trim() to index }
    .toMap()
    .filterKeys { it.isNotBlank() }


data class Localization(val score: String, val order: String, val name: String)

data class Student(val name: String, val number: Int, val score: Int?)

fun main() {
    // with open("...", "rt") as file:
    //     json.loads(file.read())
    // json.load("...")
    val localization = File("temp/localization.json").fromJson<Localization>()
    val path = "temp/sem03-1.csv"
    val file = File(path)

    println(localization)

    require(file.isFile) { "File '$path' not exists" }

    val students = file
        .bufferedReader()
        .use { reader ->
            val header = reader.readLine().toTableHeader()

            val scoreColumnIndex = header.getValue(localization.score)
            val numberColumnIndex = header.getValue(localization.order)
            val nameColumnIndex = header.getValue(localization.name)

            reader
                .lineSequence()
                .map { it.split(",") }
                .takeWhile { it[numberColumnIndex].isNotBlank() }
                .map {
                    Student(
                        it[nameColumnIndex],
                        it[numberColumnIndex].toInt(),
                        it[scoreColumnIndex].toIntOrNull()
                    )
                }
                .toList()
        }

    students
        .filter { it.score != null }
        .groupingBy { it.score!! / 5 * 5 }
        .eachCount()
        .toSortedMap()
        .forEach { println(it) }
}