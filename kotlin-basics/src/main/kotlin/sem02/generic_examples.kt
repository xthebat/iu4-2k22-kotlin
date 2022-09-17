package sem02

import org.apache.http.util.Args

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

fun generics(args: Array<String>) {
    "xxx".runCatching { length }

    "xxx".runCatchingMy { it }.value
    runCatchingMy0("xxx") { it.length }.value
    "xxx".runCatchingMy2 { it.length }.value

    10.runCatchingMy2 { it + 10 }.value
    10.runCatchingMy2 { it.toString() }.value
}