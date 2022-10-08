@file:Suppress("NOTHING_TO_INLINE")

package sem05

import java.io.ByteArrayInputStream
import java.io.DataInputStream
import java.io.InputStream


class MyDataInputStream(private val bytes: ByteArray) {
    private var position: Int = 0

    fun readInt8(): Int = bytes[position++].toInt()
    fun readInt16() = (readInt8() shl 8) or readInt8()
    fun readInt32() = (readInt16() shl 16) or readInt16()
}


inline fun InputStream.readInt8() = read()
inline fun InputStream.readInt16() = (readInt8() shl 8) or readInt8()
inline fun InputStream.readInt32() = (readInt16() shl 16) or readInt16()


fun main(args: Array<String>) {
    val bos = ByteArrayInputStream(byteArrayOf(0x30, 0x20, 0x40, 0x70, 0x30, 0x20, 0x40, 0x70))

    val bosInt32 = bos.readInt32()
    println("bosInt32 = $bosInt32")

    val stream = MyDataInputStream(byteArrayOf(0x30, 0x20, 0x40, 0x70, 0x30, 0x20, 0x40, 0x70))
    val int32 = stream.readInt32()
    val int16 = stream.readInt16()
    println("int32 = $int32 int16 = $int16")
}