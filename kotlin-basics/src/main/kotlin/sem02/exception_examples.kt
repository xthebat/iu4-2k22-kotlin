package sem02

import kotlin.system.exitProcess

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

fun String.canConvertToInt1() = runCatching { toInt() }.isSuccess

fun sumOfConvertibleStringsGood(args: Array<String>) = args
    .filter { it.canConvertToInt1() }
    .sumOf { it.toInt() }


fun sumOfConvertibleStringsBad(args: Array<String>): Int {
    var sum = 0
    for (it in args) {
        try {
            sum += it.toInt()
        } catch (error: NumberFormatException) {
            continue
        }
    }
    return sum
}

fun tryCatchExample(args: Array<String>) {
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
}


fun exceptions(args: Array<String>) {
    println("fdasfds".canConvertToInt1())

    val sumGood = sumOfConvertibleStringsGood(args)
    println("sum of convertible arguments good = $sumGood")

    val sumBad = sumOfConvertibleStringsBad(args)
    println("sum of convertible arguments bad = $sumBad")

    tryCatchExample(args)

    runCatching { test(args) }.isSuccess
}