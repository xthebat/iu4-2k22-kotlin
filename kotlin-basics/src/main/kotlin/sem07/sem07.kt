package sem07

import sem06.Student
import java.io.BufferedReader
import java.io.File

typealias Header = Map<String, Int>

fun BufferedReader.readTableHeader(): Header = readLine()
    .split(",")
    .mapIndexedNotNull { index, name ->
        val trimmed = name.trim()
        when {
            trimmed.isNotBlank() -> trimmed to index
            else -> null
        }
    }.toMap()

fun main() {
    val path = "temp/sem03-1.csv"
    val file = File(path)

    require(file.isFile) { "File '$path' not exists"}

    Student("test", 21).use {
        println("Test student for age")
        require(it.age > 20) { "Age verification failed" }
    }

    file.bufferedReader().use {
        val header = it.readTableHeader()
        val scoreColumnIndex = header["Баллы"]
        println(scoreColumnIndex)
    }
}