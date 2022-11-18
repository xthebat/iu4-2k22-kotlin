@file:Suppress("NOTHING_TO_INLINE")

package sem06

import sem05.readInt16
import sem05.readInt32
import sem05.readInt8
import java.io.*
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.starProjectedType
import kotlin.reflect.typeOf
import kotlin.system.measureTimeMillis

inline val ByteArray.string get() = toString(Charsets.ISO_8859_1)
inline val String.bytes get() = toByteArray(Charsets.ISO_8859_1)


inline val String.unhex2 get() = toInt(16).toByte()
inline val Byte.hex2 get() = (toInt() and 0xFF).toString(16)
inline val Int.hex8 get() = (toLong() and 0xFFFF_FFFF).toString(16)


inline fun String.unhexlify(): ByteArray {
    val array = chunked(2).map { it.unhex2 }
    return ByteArray(length / 2) { array[it] }
}


inline fun ByteArray.hexlify() = joinToString("") { it.hex2 }


inline fun InputStream.readInt8(): Int {
    val byte = read()
    if (byte == -1) throw EOFException()
    return byte
}

inline fun InputStream.readInt16() = (readInt8() shl 8) or readInt8()
inline fun InputStream.readInt32() = (readInt16() shl 16) or readInt16()

inline fun InputStream.readUInt8() = readInt8().toUInt() and 0xFFu
inline fun InputStream.readUInt16() = readInt16().toUInt() and 0xFFFFu
inline fun InputStream.readUInt32() = readInt32().toUInt() and 0xFFFF_FFFFu


inline fun InputStream.readWhile(capacity: Int = 32, predicate: (Int) -> Boolean): ByteArray {
    val bos = ByteArrayOutputStream(capacity)
    while (true) {
        val byte = readInt8()

        if (!predicate(byte)) {
            return bos.toByteArray()
        }

        bos.write(byte)
    }
}

inline fun InputStream.readString() = readWhile { it != 0 }.string

data class CpuContext(val eax: Int, val ecx: Int, val ebx: Int, val edx: Int)

data class Student(val name: String, val age: Int) : Closeable {
    override fun close() = println("'$name' -> closed")
}


/*
class Serializable(Protocol):

    @classmethod
    def read(cls, stream: DataInputStream) -> "Serializable": ...
*/
fun interface Reader<T> {
    fun read(stream: InputStream): T
}


@Suppress("UNCHECKED_CAST")
fun <R> Any?.cast() = this as R


class Deserializer {
    companion object {
        val default = Deserializer().apply {
            register { it.readString() }

            register { it.readUInt8().toUByte() }
            register { it.readUInt16().toUShort() }
            register { it.readUInt32() }

            register { it.readInt8().toByte() }
            register { it.readInt16().toShort() }
            register { it.readInt32() }
        }
    }

    private val readers = mutableMapOf<KType, Reader<*>>()

    fun <T: Any> deserialize(kClass: KClass<T>, stream: InputStream): T =
        deserialize(kClass.starProjectedType, stream).cast()

    fun deserialize(type: KType, stream: InputStream): Any? =
        readers.getOrPut(type) { defaultReader(type) }.read(stream)

    /*
    def defaultReader(kClass: KClass<T>, stream: InputStream):
        class KClassReader(Reader):
            def read(stream: InputStream) -> T:
                ...

        return Reader()
     */
    private fun defaultReader(type: KType) = Reader { stream ->
        val kClass = type.classifier as KClass<*>

        val constructor = kClass.constructors.single()

        // args = {it: it.deserialize(stream) for it in constructor.parameters}
        val args = constructor.parameters.associateWith { deserialize(it.type, stream) }

        // return constructor(**args)
        constructor.callBy(args)
    }

    fun <T: Any> register(type: KType, reader: Reader<T>) {
        require(type !in readers) { "Deserializer for type $type already registered"}
        readers[type] = reader
    }

    inline fun <reified T: Any> register(reader: Reader<T>) = register(typeOf<T>(), reader)
}

inline fun <T: Any> KClass<T>.deserialize(
    stream: InputStream,
    deserializer: Deserializer = Deserializer.default
) = deserializer.deserialize(this, stream)


inline fun <T: Any> KClass<T>.deserialize(
    bytes: ByteArray,
    deserializer: Deserializer = Deserializer.default
) = deserialize(ByteArrayInputStream(bytes), deserializer)


data class OuterWeirdClass(val field3040: Int, val somethingWeird: SomethingWeird)

data class SomethingWeird(val field9988: Int, val default: Boolean = false)


fun main(args: Array<String>) {
    println("hello world")

    Deserializer.default.register {
        SomethingWeird(it.readInt32())
    }

    val somethingWeird = SomethingWeird::class.deserialize("00009988".unhexlify())
    println(somethingWeird)

    measureTimeMillis {
        repeat(1000) {
            val outerWeirdClass = OuterWeirdClass::class.deserialize("0000304000009988".unhexlify())
//            println(outerWeirdClass)
        }
    }

    val time = measureTimeMillis {
        repeat(10000) {
            val outerWeirdClass = OuterWeirdClass::class.deserialize("0000304000009988".unhexlify())
//            println(outerWeirdClass)
        }
    }
    println("time elapsed = $time")

    val serializedStudent = "4E/|_C_7OPbI".bytes + "0000000012".unhexlify()
    val student = Student::class.deserialize(serializedStudent)
    println(student)

    val data = args[0].bytes
    val context = CpuContext::class.deserialize(data)
    val sumof = context.eax + context.ebx
    println(sumof.hex8)
    println(context)
}