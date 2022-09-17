import java.io.File
import kotlin.system.exitProcess

fun sum(a: Int, b: Int) = a + b

operator fun String.div(other: String): String = File(this, other).path

inline fun String.for_each(count: Int, action: (Char) -> Unit) {
    var i = 0
    for (char in this) {
        action(char)
        if (++i >= count)
            break
    }
}


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


fun test(args: Array<String>): String {
    if (args.size < 5)
        throw IndexOutOfBoundsException("Not enough arguments, required at least 2 args but got ${args.size}")
    return "We have enough arguments"
}


fun String.canConvertToInt0(): Boolean {
    try {
        toInt()
    } catch (error: NumberFormatException) {
        return false
    }
    return true
}


class Result<T>(val value: T?, val error: Throwable?)

inline fun Any.runCatchingMy(block: (Any) -> Any) = try {
    Result(block(this), null)
} catch (error: Throwable) {
    Result(null, error)
}

inline fun <Type, Result> runCatchingMy0(self: Type, block: (Type) -> Result) = try {
    Result(block(self), null)
} catch (error: Throwable) {
    Result(null, error)
}

inline fun <T, R> runCatchingMy1(self: T, block: (T) -> R) = try {
    Result(block(self), null)
} catch (error: Throwable) {
    Result(null, error)
}


inline fun <T, R> T.runCatchingMy2(block: (T) -> R) = try {
    Result(block(this), null)
} catch (error: Throwable) {
    Result(null, error)
}


fun String.canConvertToInt1() = runCatching { toInt() }.isSuccess

fun sem01(args: Array<String>) {
    "programs" / "ida"
//    "programs/ida"

    val r = "programs" / "ida"
    val maxCount = 2

    "xxx".runCatching { this.length }
//    "xxx".runCatchingMy { it. }
    "xxx".runCatchingMy { it }.value
    runCatchingMy0("xxx") { it.length }.value
    "xxx".runCatchingMy2 { it.length }.value

    10.runCatchingMy2 { it + 10 }.value
    10.runCatchingMy2 { it.toString() }.value

    r.for_each(maxCount) { chr ->
        println("chr = $chr / maxCount = $maxCount")
    }

    println(r)

    val s = sum(1, 2)

    var i = 0
    while (i < args.size) {
        println("args[$i] = ${args[i++]}")
    }

    for (arg in args) {
        println(arg)
    }

    for (k in args.indices) {
        println(k)
    }

//    args.forEach { println(it) }

    println("----------------------")


//  [int(it.remove_prefix("test")) * 2 for it in args if it.endswith("2")]
//  foreach(
//    lambda it: println(it),
//    map(
//      lambda it: int(it.remove_prefix("test")) * 2,
//      filter(lambda it: it.endswith("2"), args)
//    )
//  )

    args
        .filter { it.endsWith("2") }
        .map {
            return@map it.removePrefix("test").toInt() * 2
        }
        .forEachIndexed { i, it ->
            println("args[$i] -> ${args[i]}")
        }
}


fun exceptions(args: Array<String>) {
    println("fdasfds".canConvertToInt1())

    val sum = args
        .filter { it.canConvertToInt1() }
        .sumOf { it.toInt() }

    var s1 = 0
    for (it in args) {
        try {
            s1 += it.toInt()
        } catch (error: NumberFormatException) {
            continue
        }
    }


//    val device = Usb.open()
    val message = try {
        test(args)
    } catch (error: IndexOutOfBoundsException) {
        println(error.message)
        "Not enough arguments"
    } catch (error: Exception) {
        println("Error: $error")
        exitProcess(-1)
    }

    println("message = $message")

    runCatching { test(args) }.isSuccess
}


fun main(args: Array<String>) {
//    sem03:
//    nullability
//    type conversion
//    collections
//    simple classes
//    get restapi + json
    println("Hello World!")
}