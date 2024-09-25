package labs.lab1

import java.awt.image.BufferedImage
import kotlin.math.sqrt
import kotlin.math.log2

object ShannonEntropy {

    fun calculate(list: List<Double>): Double {
        return -list.sumOf { it * log2(it) }
    }

    fun calculate(image: BufferedImage, strategy: ShannonEntropyStrategy): Double {
        val n = image.width * image.height.toDouble()
        val acc = computeIntensity(getRGBStreamFromImage(image), strategy)
        System.gc()
        return calculate(acc.values.map { it / n })
    }

    fun computeIntensity(rgbList: List<Triple<Int, Int, Int>>, strategy: ShannonEntropyStrategy): MutableMap<Int, Int> {
        val acc = mutableMapOf<Int, Int>()
        rgbList.forEach {
            val d = strategy.apply(it.first, it.second, it.third)
            acc.compute(d) { _, v -> if (v == null) 1 else v + 1 }
        }
        return acc
    }

    fun getRGBStreamFromImage(image: BufferedImage): List<Triple<Int, Int, Int>> {
        val rgbList = mutableListOf<Triple<Int, Int, Int>>()
        repeat(image.width) { x ->
            repeat(image.height) { y ->
                val pixelColor = image.getRGB(x, y)
                val r = (pixelColor shr 16) and 0xFF
                val g = (pixelColor shr 8) and 0xFF
                val b = pixelColor and 0xFF
                rgbList.add(Triple(r, g, b))
            }
        }
        return rgbList
    }

    enum class ShannonEntropyStrategy(val apply: (Int, Int, Int) -> Int) {
        AVERAGE({ r, g, b -> ((1.0 / 3.0) * (r + g + b)).toInt() }),
        NORMALIZED_VECTOR_LENGTH({ r, g, b -> ((1.0 / sqrt(3.0)) * (r * r + g * g + b * b)).toInt() }),
        EYE({ r, g, b -> (0.229 * r + 0.587 * g + 0.114 * b).toInt() });
    }
}