package labs.lab3

object EncodingTools {
    fun encodeToString(msg: String, map: Map<Byte, String>): String {
        val builder = StringBuilder()
        msg.forEach { builder.append(map[it.code.toByte()]) }
        return builder.toString()
    }

    fun encodeToString(msg: ByteArray, map: Map<Byte, String>): String {
        val builder = StringBuilder()
        msg.forEach { builder.append(map[it]) }
        return builder.toString()
    }

    fun decodeToString(msg: String, map: Map<Byte, String>): String {
        val codeToByte = map.map { it.value to it.key }.toMap()
        val builder = StringBuilder()
        var current = ""
        msg.forEach {
            current += it
            if (codeToByte.containsKey(current)) {
                builder.append(codeToByte[current]!!.toInt().toChar())
                current = ""
            }
        }
        return builder.toString()
    }

    fun calculateInformationPerBits(msg: String, map: Map<Byte, String>): Double {
        val p = msg.groupingBy { it }.eachCount()
        var d = 0.0
        p.forEach { (k, v) ->
            d += map[k.code.toByte()]!!.length * v.toDouble() / msg.length
        }
        return d
    }
}