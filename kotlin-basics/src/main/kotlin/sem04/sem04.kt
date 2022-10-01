package sem04

import java.util.*


operator fun <T> Array<T>.get(key: String): T = this[key.toInt()]


fun collectionsExamples(args: Array<String>) {
    //    args[2] = "ffff"
    val q = args["0"]

    val arrayInts = arrayOf<Int>(1, 2, 3)
    val listInts = listOf(1, 2, 3)
//    listInts[0] = 1
//    listInts.add(1)
    val mutableListInts = mutableListOf(1, 2, 3)
    mutableListInts.add(1)
    mutableListInts[2] = 1
    mutableListInts.removeIf { it == 0 }

    val arrayList = arrayListOf("st", "micron", "st")
    arrayList.add("sss")
    arrayList[1] = "fff"


    val stack = LinkedList<String>()
    stack.push("qqq")
    stack.pop()

    val immutableSet = setOf(1, 2, 1, 1)
//    immutableSet["f"]
//    immutableSet.add()

    val unique = arrayList.toSet().toList()
    println("$arrayList, $unique")

    val mutableSet = mutableSetOf(1, 2, 3)
    if (mutableSet.add(3)) {
        println("3 is not in set")
    }

    val mapping = mapOf(
        "string2" to 2,
        "string1" to 1
    )

    val value = mapping["string2"]
    val value1 = mapping["string2"] ?: throw NoSuchElementException("...")
    val value5 = mapping["string2"] ?: { args.count { it.toIntOrNull() != null } }  // lazy
    val value2 = mapping.getValue("string2")
    val value3 = mapping.getOrDefault("string2", args.count { it.toIntOrNull() != null })
    val value4 = mapping.getOrElse("string3") { args.count { it.toIntOrNull() != null } }  // lazy
//  mapping.get(key) or count(args, lambda it: ...)
//  mapping.get(key) if key in mapping else count(...)
//  map(lambda key, value: key + value, dictionary.items())
//  map(lambda key_value: key_value[0] + key_value[1], dictionary.items())
//  map(lambda it: it[0] + it[1], dictionary.items())

    mapping.forEach { (key, value) ->

    }
    mapping.forEach { key, value ->

    }
    println(value2 + value1 + 1)
}


data class Student(val name: String, val index: Int = 0, val score: Int = 0) {
    companion object {
        fun from(string: String = "Name, 0, 100"): Student {
            val tokens = string.split(",").map { it.trim() }
            return Student(tokens[0], tokens[1].toInt(), tokens[2].toInt())
        }

        fun from(student: Student) = Student(student.name, student.index, student.score)
    }

    fun say() = println("My name $name")

    init {
        println("I am created: $name")
    }
}

//fun studentFromString(string: String): Student {
//
//}

class AntiPatternStudent(val name: String, friendString: String = "Alisa, Bob, Unknown") {
    val friends = friendString.split(",").map { it.trim() }

//    val friends = mutableListOf<AntiPatternStudent>()
//    init {
//        for (it in friendString.split(",")) {
//            ...
//        }
//    }
}

// toString
// hashCode()
// equals()

data class MutableStudent(var name: String, var index: Int, var score: Int)


fun main(args: Array<String>) {
//    sem04:
//    collections:
//    - Array,
//    - List, MutableList --> ArrayList, LinkedList
//    - Set, MutableSet --> HashSet
//    - Map, MutableMap --> HashMap
//    simple classes, object, enum
//    sem03-0, 1
//    files from args

    val vanya = Student("Vanya")
    val vanyaFromString = Student.from("VanyaFromString, 20, 59")
    val vanyaFromVanya = Student.from(vanya)
    val mutableVanya = MutableStudent("Vanya", 0, 100)

//    mutableVanya = MutableStudent("fff", 11, 22)
    mutableVanya.name = "Alisa"

    println(mutableVanya)

    println(vanya)
    vanya.say()

//    classmethod vs staticmethod
}