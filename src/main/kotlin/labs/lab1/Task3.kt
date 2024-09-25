package labs.lab1

import org.jetbrains.kotlinx.kandy.dsl.plot
import org.jetbrains.kotlinx.kandy.letsplot.export.save
import org.jetbrains.kotlinx.kandy.letsplot.feature.layout
import org.jetbrains.kotlinx.kandy.letsplot.layers.bars
import org.jetbrains.kotlinx.kandy.letsplot.x
import org.jetbrains.kotlinx.kandy.letsplot.y
import java.io.File
import java.util.stream.IntStream
import javax.imageio.ImageIO

fun main() {
    val image = ImageIO.read(File("res/lab1/image.png"))
    val rgbList = ShannonEntropy.getRGBStreamFromImage(image)
    buildRGBChart(rgbList.map { it.first }.groupingBy { it }.eachCount(), "R")
    buildRGBChart(rgbList.map { it.second }.groupingBy { it }.eachCount(), "G")
    buildRGBChart(rgbList.map { it.third }.groupingBy { it }.eachCount(), "B")
    buildRGBChart(ShannonEntropy.computeIntensity(rgbList, ShannonEntropy.ShannonEntropyStrategy.NORMALIZED_VECTOR_LENGTH), "I")
}

private fun buildRGBChart(map: Map<Int, Int>, titleName: String) {
    val plotData = mapOf(
        "color" to IntStream.range(0, 256).boxed().toList(),
        "count" to IntStream.range(0, 256).boxed().map { map.getOrDefault(it, 0) }.toList()
    )
    plot(plotData) {
        x("color")
        y("count")
        bars {

        }
        layout {
            title = titleName
            size = 700 to 450
        }
    }.save("${titleName}.png", path = "res/lab1")
}