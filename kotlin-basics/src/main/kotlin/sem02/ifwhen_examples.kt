package sem02

import kotlin.system.exitProcess

//    (args.size != 0) ? "We have arguments" : "We haven't got arguments"
//     "We have arguments" if args.size != 0 else "We haven't got arguments"
fun haveArgs0(args: Array<String>) = if (args.isNotEmpty()) "We have arguments" else "We haven't got arguments"

fun haveArgs2(args: Array<String>): String {
    if (args.size == 0) {
        println("We haven't got arguments")
    } else if (args.size == 1) {
        println("We have 1 arguments")
    } else {
        println("We have many arguments")
    }

    when (args.size) {
        0 -> println("We haven't got arguments")
        1 -> println("We have 1 arguments")
//        else -> exitProcess(-1)
    }

    val result = if (args.size == 0) {
        "We haven't got arguments"
    } else if (args.size == 1) {
        "We have 1 arguments"
    } else {
        "We have many arguments"
    }

    return result
}

fun haveArgs1(args: Array<String>) = when (args.size) {
    0 -> "We haven't got arguments"
    1 -> "We have 1 arguments"
    in 2..20 -> "We have many arguments"
    in 21..30 -> {
        println(".... 2")
        "We have many arguments 2"
    }
    in 31..40 -> {
        println(".... 3")
        "We have many arguments 3"
    }
    else -> exitProcess(-1)
}

fun ifwhen(args: Array<String>) {
    println(haveArgs0(args))
    println(haveArgs1(args))
    println(haveArgs2(args))
}