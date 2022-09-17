import sem01.div
import sem01.for_each
import sem01.sum

fun main(args: Array<String>) {
    println("Hello World!")

    "programs" / "ida"
//    "programs/ida"

    val r = "programs" / "ida"
    val maxCount = 2

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