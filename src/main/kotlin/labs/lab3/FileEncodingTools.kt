package labs.lab3

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.math.BigInteger


object FileEncodingTools {
    fun encodeMap(map: Map<Byte, String>, dos: DataOutputStream) {
        dos.write(map.size)
        map.forEach { (k, v) ->
            dos.write(k.toInt())
            dos.write(v.length)
            dos.writeLong(v.toLong(2))
        }
    }

    fun decodeMap(dis: DataInputStream): Map<Byte, String> {
        val map = mutableMapOf<Byte, String>()
        repeat(dis.read()) {
            val b = dis.read().toByte()
            val size = dis.read()
            val s = dis.readLong().toString(2)
            map[b] = s.padStart(size, '0')
        }
        return map
    }

    fun encode(bytes: ByteArray, map: Map<Byte, String>): ByteArray {
        val bos = ByteArrayOutputStream()
        val dos = DataOutputStream(bos)
        encodeMap(map, dos)
        var bit = BigInteger.ZERO
        var p = 0
        bytes.forEach {
            val s = map[it]!!
            bit = BitArraySimple.addString(s, bit)
            p += s.length
        }
        val enBytes = bit.toByteArray()
        dos.writeInt(enBytes.size)
        dos.writeInt(p)
        bos.write(enBytes)
        return bos.toByteArray()
    }

    fun decode(bytes: ByteArray): ByteArray {
        val bis = ByteArrayInputStream(bytes)
        val dis = DataInputStream(bis)
        val bos = ByteArrayOutputStream()
        val map = decodeMap(dis).map { it.value to it.key }.toMap()
        val size = dis.readInt()
        var pSize = dis.readInt()
        val read = bis.readNBytes(size)
        val big = BigInteger(read)
        var s = ""
        while (pSize > 0) {
            s += BitArraySimple.read(big, pSize - 1)
            pSize--
            if (map.containsKey(s)) {
                bos.write(map[s]!!.toInt())
                s = ""
            }
        }

        return bos.toByteArray()
    }
}