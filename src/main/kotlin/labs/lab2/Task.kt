package labs.lab2

import labs.lab1.ShannonEntropy
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.log2
import kotlin.math.min
import kotlin.math.roundToInt

fun main() {
    val image = ImageIO.read(File("res/lab2/image.png"))
    val grayscale = convertToGrayScale(image).write("gray")
    val grayscaleEntropy = ShannonEntropy.calculate(grayscale, ShannonEntropy.ShannonEntropyStrategy.NORMALIZED_VECTOR_LENGTH)
    println("Grayscale entropy: $grayscaleEntropy")
    val discretized2 = discretize(grayscale, 2).write("d2")
    val discretized4 = discretize(grayscale, 4).write("d4")

    val quantized8Levels = quantize(grayscale, 8).write("q8")
    val quantized16Levels = quantize(grayscale, 16).write("q16")
    val quantized64Levels = quantize(grayscale, 64).write("q64")

    val discretized2quantized8Levels = quantize(discretized2, 8).write("d2q8")
    val discretized2quantized16Levels = quantize(discretized2, 16).write("d2q16")
    val discretized2quantized64Levels = quantize(discretized2, 64).write("d2q64")
    val discretized4quantized8Levels = quantize(discretized4, 8).write("d4q8")
    val discretized4quantized16Levels = quantize(discretized4, 16).write("d4q16")
    val discretized4quantized64Levels = quantize(discretized4, 64).write("d4q64")

    val q8Entropy = ShannonEntropy.calculate(quantized8Levels, ShannonEntropy.ShannonEntropyStrategy.NORMALIZED_VECTOR_LENGTH)
    val q16Entropy = ShannonEntropy.calculate(quantized16Levels, ShannonEntropy.ShannonEntropyStrategy.NORMALIZED_VECTOR_LENGTH)
    val q64Entropy = ShannonEntropy.calculate(quantized64Levels, ShannonEntropy.ShannonEntropyStrategy.NORMALIZED_VECTOR_LENGTH)

    val d2q8Entropy = ShannonEntropy.calculate(discretized2quantized8Levels, ShannonEntropy.ShannonEntropyStrategy.NORMALIZED_VECTOR_LENGTH)
    val d2q16Entropy = ShannonEntropy.calculate(discretized2quantized16Levels, ShannonEntropy.ShannonEntropyStrategy.NORMALIZED_VECTOR_LENGTH)
    val d2q64Entropy = ShannonEntropy.calculate(discretized2quantized64Levels, ShannonEntropy.ShannonEntropyStrategy.NORMALIZED_VECTOR_LENGTH)
    val d4q8Entropy = ShannonEntropy.calculate(discretized4quantized8Levels, ShannonEntropy.ShannonEntropyStrategy.NORMALIZED_VECTOR_LENGTH)
    val d4q16Entropy = ShannonEntropy.calculate(discretized4quantized16Levels, ShannonEntropy.ShannonEntropyStrategy.NORMALIZED_VECTOR_LENGTH)
    val d4q64Entropy = ShannonEntropy.calculate(discretized4quantized64Levels, ShannonEntropy.ShannonEntropyStrategy.NORMALIZED_VECTOR_LENGTH)

    println("Entropy for quantized image with 8 levels = $q8Entropy")
    println("Entropy for quantized image with 16 levels = $q16Entropy")
    println("Entropy for quantized image with 64 levels = $q64Entropy")
    println("Entropy for discretized image with 2 levels and quantized with 8 levels = $d2q8Entropy")
    println("Entropy for discretized image with 2 levels and quantized with 16 levels = $d2q16Entropy")
    println("Entropy for discretized image with 2 levels and quantized with 64 levels = $d2q64Entropy")
    println("Entropy for discretized image with 4 levels and quantized with 8 levels = $d4q8Entropy")
    println("Entropy for discretized image with 4 levels and quantized with 16 levels = $d4q16Entropy")
    println("Entropy for discretized image with 4 levels and quantized with 64 levels = $d4q64Entropy")

    val restored2 = restoreImage(discretized2, 2).write("r2")
    val restored4 = restoreImage(discretized4, 4).write("r4")

    val kullbackQ8 = kullbackLeiblerDivergence(grayscale, quantized8Levels)
    val kullbackQ16 = kullbackLeiblerDivergence(grayscale, quantized16Levels)
    val kullbackQ64 = kullbackLeiblerDivergence(grayscale, quantized64Levels)

    val kullbackD2Q8 = kullbackLeiblerDivergence(grayscale, discretized2quantized8Levels)
    val kullbackD2Q16 = kullbackLeiblerDivergence(grayscale, discretized2quantized16Levels)
    val kullbackD2Q64 = kullbackLeiblerDivergence(grayscale, discretized2quantized64Levels)
    val kullbackD4Q8 = kullbackLeiblerDivergence(grayscale, discretized4quantized8Levels)
    val kullbackD4Q16 = kullbackLeiblerDivergence(grayscale, discretized4quantized16Levels)
    val kullbackD4Q64 = kullbackLeiblerDivergence(grayscale, discretized4quantized64Levels)

    println("Kullback-Leiber Distance for quantized image with 8 levels = $kullbackQ8")
    println("Kullback-Leiber Distance for quantized image with 16 levels = $kullbackQ16")
    println("Kullback-Leiber Distance for quantized image with 64 levels = $kullbackQ64")
    println("Kullback-Leiber Distance for discretized image with 2 levels and quantized with 8 levels = $kullbackD2Q8")
    println("Kullback-Leiber Distance for discretized image with 2 levels and quantized with 16 levels = $kullbackD2Q16")
    println("Kullback-Leiber Distance for discretized image with 2 levels and quantized with 64 levels = $kullbackD2Q64")
    println("Kullback-Leiber Distance for discretized image with 4 levels and quantized with 8 levels = $kullbackD4Q8")
    println("Kullback-Leiber Distance for discretized image with 4 levels and quantized with 16 levels = $kullbackD4Q16")
    println("Kullback-Leiber Distance for discretized image with 4 levels and quantized with 64 levels = $kullbackD4Q64")
}

private fun restoreImage(image: BufferedImage, step: Int): BufferedImage {
    val i = BufferedImage(image.width * step, image.height * step, image.type)
    repeat(i.width) { x ->
        repeat(i.height) { y ->
            i.setRGB(x, y, image.getRGB(x / step, y / step))
        }
    }
    return i
}

private fun BufferedImage.write(name: String): BufferedImage {
    ImageIO.write(this, "png", File("res/lab2/${name}.png"))
    return this
}

private fun convertToGrayScale(image: BufferedImage): BufferedImage {
    val gray = BufferedImage(image.width, image.height, BufferedImage.TYPE_BYTE_GRAY)
    val g = gray.graphics
    g.drawImage(image, 0, 0, null)
    return gray
}

private fun discretize(image: BufferedImage, step: Int): BufferedImage {
    val i = BufferedImage(image.width / step, image.height / step, image.type)
    repeat(i.width) { x ->
        repeat(i.height) { y ->
            i.setRGB(x, y, image.getRGB(x * step, y * step))
        }
    }
    return i
}

private fun quantize(image: BufferedImage, level: Int): BufferedImage {
    val i = BufferedImage(image.width, image.height, image.type)
    repeat(i.width) { x ->
        repeat(i.height) { y ->
            i.setRGB(x, y, quantizeColor(image.getRGB(x, y), level))
        }
    }
    return i
}

private fun quantizeColor(color: Int, level: Int): Int {
    val b = quantizeValue(color and 0xFF, 256 / level)
    val g = quantizeValue((color shr 8) and 0xFF, 256 / level)
    val r = quantizeValue((color shr 16) and 0xFF, 256 / level)
    val a = color and 0xFF000000.toInt()
    return a or (r shl 16) or (g shl 8) or b
}

private fun quantizeValue(value: Int, level: Int): Int {
    return (value.toDouble() / level).roundToInt() * level
}

private fun kullbackLeiblerDivergence(orig: BufferedImage, copy: BufferedImage): Double {
    val origSize = orig.width * orig.height.toDouble()
    val copySize = copy.width * copy.height.toDouble()
    val origBrightness = ShannonEntropy.computeIntensity(ShannonEntropy.getRGBStreamFromImage(orig), ShannonEntropy.ShannonEntropyStrategy.NORMALIZED_VECTOR_LENGTH)
        .values.map { it / origSize }
    val copyBrightness = ShannonEntropy.computeIntensity(ShannonEntropy.getRGBStreamFromImage(copy), ShannonEntropy.ShannonEntropyStrategy.NORMALIZED_VECTOR_LENGTH)
        .values.map { it / copySize }
    var d = 0.0
    repeat(min(origBrightness.size, copyBrightness.size)) {
        if (origBrightness[it] <= 0) return@repeat
        if (copyBrightness[it] <= 0) return@repeat
        d += copyBrightness[it] * log2(copyBrightness[it] / origBrightness[it])
    }
    return d
}