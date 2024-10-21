package labs.lab3

fun main() {
    val p = doubleArrayOf(0.27, 0.15, 0.45, 0.13)
    val text = "BACCBCCCCCADBAAABABABACDCBCACC"
    val input = ByteArray(text.length / 2)
    val pp = DoubleArray(16) { p[it / 4] * p[it % 4] }
    repeat(text.length / 2) {
        val code = parseCharToInt(text.elementAt(it * 2)) * 4 +
                parseCharToInt(text.elementAt(it * 2 + 1))
        input[it] = code.toByte()
    }
    val tree = HuffmanEncoding.createTree(input)
//    val tree = HuffmanEncoding.createTree(ByteArray(16)  { it.toByte() }, pp)
    val map = HuffmanEncoding.createCodesMap(tree)
    val encoded = EncodingTools.encodeToString(input, map)
    println(encoded)
    println("l = ${encoded.length}")
    map.entries.sortedBy { it.key }.forEach { (k, v) ->
        println("${parseByteToString(k)} = $v")
    }
    println("One letter encoding")
    val mapA = HuffmanEncoding.createCodesMap(text.toByteArray())
    val encodedA = EncodingTools.encodeToString(text, mapA)
    println(encodedA)
    println("l = ${encodedA.length}")
    mapA.forEach { (k, v) ->
        println("${k.toChar()} = $v")
    }
}

private fun parseByteToString(b: Byte): String {
    return "" + parseIntToChar(b / 4) + parseIntToChar(b % 4)
}

private fun parseIntToChar(b: Int): Char {
    return when(b) {
        0 -> 'A'
        1 -> 'B'
        2 -> 'C'
        3 -> 'D'
        else -> 'X'
    }
}

private fun parseCharToInt(i: Char) = when (i) {
    'A' -> 0
    'B' -> 1
    'C' -> 2
    'D' -> 3
    else -> -1
}