package labs.lab5

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import kotlin.io.path.readLines
import kotlin.math.ceil
import kotlin.math.log2

fun main() {
    val dir = Paths.get("res/lab5")
    val inputFile = dir.resolve("code.txt")
    val outputFile = dir.resolve("decode.txt")
    val encodedCode = inputFile.readLines().joinToString("") { it.replace("\\s+".toRegex(), "") }
    val code = encodedCode.map { it.digitToInt() }.toMutableList()
    val n = code.size
    val r = ceil(log2(n.toFloat())).toInt()
    val n0 = n - r
    var syndrome = 0
    repeat(r) {
        val check = 1 shl it
        var k = 0
        val p = StringBuilder("")
        val x = StringBuilder()
        for (i in code.indices) {
            if (((i + 1) and check) == 0) continue
            k = k xor code[i]
            p.append("x${i + 1} ^ ")
            x.append(" ${code[i]} ^")
        }
        println("${p.dropLast(2)}=${x.dropLast(1)}= $k")
        syndrome = syndrome or (k shl it)
    }
    println("Syndrome: ${syndrome.toString(2).padStart(r, '0')}")
    if (syndrome != 0) {
        println("Because syndrome isn't equals to 0 we have an error.")
        println("Invert ${syndrome}th bit")
        code[syndrome - 1] = if (code[syndrome - 1] == 1) 0 else 1
    }
    val decodedCode = StringBuilder()
    for (i in code.indices) {
        if ((i + 1).countOneBits() == 1) continue
        decodedCode.append(if (code[i] == 1) "1" else "0")
    }
    val decodedString = decodedCode.toString().chunked(4).joinToString(" ")
    println("Decoded message: $decodedString")
    Files.write(outputFile, decodedString.toByteArray(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)
}