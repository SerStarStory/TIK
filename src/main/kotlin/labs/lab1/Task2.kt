package labs.lab1

import kotlin.random.Random

fun main() {
    val random = Random(System.currentTimeMillis())
    val count = random.nextInt(11, 20)
    val list = Array(count) { random.nextInt(1, 10) / count.toDouble() }.toList()
    list.forEach { println(it) }
    println(ShannonEntropy.calculate(list))
}