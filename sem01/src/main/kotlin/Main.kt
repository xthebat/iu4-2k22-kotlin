import java.io.File

fun sum(a: Int, b: Int) = a + b


operator fun String.div(other: String) = File(this, other).path

fun String.for_each(count: Int, action: (Char) -> Unit) {
    var i = 0
    for (char in this) {
        action(char)
        if (++i >= count)
            break
    }
}


fun main(args: Array<String>) {
    println("Hello World!")

    "programs" / "ida"
//    "programs/ida"

    val r = "programs" / "ida"
    val maxCount = 2

    r.for_each(maxCount) { chr -> println(chr) }

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
        .map { it.removePrefix("test").toInt() * 2 }
        .forEachIndexed { i, it -> println("args[$i] -> ${args[i]}") }
}