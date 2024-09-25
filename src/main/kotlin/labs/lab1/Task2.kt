package labs.lab1

import java.util.stream.IntStream
import kotlin.random.Random

fun main() {
    val random = Random(13)
    val count = random.nextInt(11, 20)
    val list = Array(count) { random.nextInt(1, 10) / count.toDouble() }.toList()
    println(ShannonEntropy.calculate(list))
}