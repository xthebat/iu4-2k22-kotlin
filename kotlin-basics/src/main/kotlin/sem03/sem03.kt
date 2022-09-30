package sem03


inline fun <reified T> T.definedType(): String = T::class.qualifiedName ?: "unknown"

fun Any.actualType(): String = this::class.qualifiedName ?: "unknown"

fun caseGetTypeName(arg: Any) {
    println("definedType=${arg.definedType()} actualType=${arg.actualType()}")
}

inline fun Short.toUnsignedInt(): Int = toInt() and 0xFFFF
inline fun Int.toUnsignedLong(): Long = toLong() and 0xFFFF_FFFFL

fun sumOfInts(a: Int, b: Int) = a + b

fun typingSystem() {
    var intValue = 10
    val intHexValue = 0x10
    val negativeIntValue = -10
    val byteValue: Byte = 10
    val shortValue: Short = 10
    val longValue: Long = 10
    val longLiteralValue = 10L

    if (negativeIntValue < intValue) {
        println("negativeIntValue < intValue")
    }

    println(negativeIntValue.toUnsignedLong())
    println(intValue.toUnsignedLong())
    if (negativeIntValue.toUnsignedLong() < intValue.toUnsignedLong()) {
        println("negativeIntValue.toUnsignedLong() < intValue.toUnsignedLong()")
    }

    intValue = byteValue.toInt()
    println(intValue.toString(16))
    sumOfInts(byteValue.toInt(), shortValue.toUnsignedInt())

    val floatValue = 10.0f
    val doubleValue = 10.0

    val charValue = 'a'

    val stringValue = "string value"

    val intArrayValue: IntArray = intArrayOf(1, 2, 3, 4)   // int[]
    val charArrayValue: CharArray = charArrayOf('a', 'b', 'c')  // char[]
//   1, 2, 3, 4
    val byteArrayValue: ByteArray = byteArrayOf(1, 2, 3, 4)  // byte[]

    byteArrayValue[0] = 0x10

    val arrayOfByteValue: Array<Byte> = arrayOf<Byte>(1, 2, 3, 4)  // Array<>
    val arrayOfIntValue: Array<Int> = arrayOf(1, 2, 3, 4)
}

var globalFackingVariable: Int? = null

fun InitializelGlobalFackingVariable() {
    globalFackingVariable = 0xDEADBEEF.toInt()
}

fun nullability(value: Int?, string: String?): Int {
    var intValue = 10
    var nullableIntValue: Int? = null

//    if (value != null) {
//        intValue = value
//    }

//    python
//    intValue = value or []
//    intValue = func and func(10)

    intValue = value ?: 10

//    string.length
//    nullableIntValue = string and len(string)
    nullableIntValue = string?.length

    checkNotNull(string) {
        "GlobalFackingVariable was not initialized, please run InitializelGlobalFackingVariable "
    }

    val useRun = string?.run { length }
    val useLet = string?.let {
        println("test")
        it.length
    }

    val useIf = if (string != null) {
        println("test")
        string.length
    } else {
        null
    }

//    if (globalFackingVariable != null) {
//        intValue + globalFackingVariable
//    }

    val nonNullValueGood0: Int = checkNotNull(globalFackingVariable) {
        "GlobalFackingVariable was not initialized, please run InitializelGlobalFackingVariable "
    }

    val nonNullValueGood1: Int = requireNotNull(globalFackingVariable) {
        "GlobalFackingVariable was not initialized, please run InitializelGlobalFackingVariable "
    }

    val nonNullValueBad: Int = globalFackingVariable!!

    val sumOfValues = globalFackingVariable?.let { intValue + it }

    val lGlobalFackingVariable = globalFackingVariable
    val useLocalVariable = if (lGlobalFackingVariable != null) intValue + lGlobalFackingVariable else null

    return nonNullValueGood0
}

fun main(args: Array<String>) {
//    caseGetTypeName(10)
//    caseGetTypeName("20")

//    typingSystem()

    InitializelGlobalFackingVariable()
    nullability(10, "ff")
//    sem03:
//    erasable generics +
//    nullability +
//    type conversion +


//    sem04:
//    collections: Array, List, ArrayList, Set, MutableSet, Map, MutableMap
//    simple classes, object, enum
//    get restapi + json
}