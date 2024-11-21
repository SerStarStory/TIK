package labs.lab5

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import kotlin.io.path.readLines
import kotlin.math.log2
import kotlin.math.pow

fun main() {
    val dir = Paths.get("res/lab5")
    val inputFile = dir.resolve("input.txt")
    val outputFile = dir.resolve("code.txt")
    val codeString = inputFile.readLines().joinToString("") { it.replace("\\s+".toRegex(), "") }
    val n0 = codeString.length
    val r = (log2(n0.toDouble()).toInt()..n0).first { 2.0.pow(it) - 1 >= n0 + it }
    val n = n0 + r
    println("Code parameters: \nn0 = $n0\nr = $r\nn = $n")
    val code = IntArray(n) { 2 }
    var j = 0
    for (k in 3..n) {
        if (k.countOneBits() == 1) continue
        code[k - 1] = if (codeString[j++] == '1') 1 else 0
    }
    //println(IntArray(n) { it + 1 }.toList())
    //println(code.toList())
    repeat(r) {
        val check = 1 shl it
        var k = 0
        val p = StringBuilder("x${check} = ")
        val x = StringBuilder()
        for (i in code.indices) {
            if ((i + 1).countOneBits() == 1) continue
            if (((i + 1) and check) == 0) continue
            k = k xor code[i]
            p.append("x${i + 1} ^ ")
            x.append(" ${code[i]} ^")
        }
        println("${p.dropLast(2)}=${x.dropLast(1)}= $k")
        code[check - 1] = k
    }
    val encodedCode = code.joinToString("") { it.toString() }
    Files.write(outputFile, encodedCode.toByteArray(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)
}