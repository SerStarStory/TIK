package labs.lab1

import java.io.File
import javax.imageio.ImageIO

fun main() {
    val png = ImageIO.read(File("res/lab1/image.png"))
    val jpg = ImageIO.read(File("res/lab1/image.jpg"))

    println("Entropy of png file: ${ShannonEntropy.calculate(png, ShannonEntropy.ShannonEntropyStrategy.NORMALIZED_VECTOR_LENGTH)}")
    println("Entropy of jpg file: ${ShannonEntropy.calculate(jpg, ShannonEntropy.ShannonEntropyStrategy.NORMALIZED_VECTOR_LENGTH)}")
}