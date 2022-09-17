package sem01

import java.io.File

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