package labs.lab3

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

fun main() {
    val dir = Paths.get("res/lab3")
    val inputFile = dir.resolve("image.txt")
    val inputBytes = Files.readAllBytes(inputFile)

    val huffmanOutputFile = dir.resolve("image.huffman")
    val huffmanDecodedOutputFile = dir.resolve("image_huffman.txt")
    val huffmanEncodedBytes = FileEncodingTools.encode(inputBytes, HuffmanEncoding.createCodesMap(inputBytes))
    Files.write(huffmanOutputFile, huffmanEncodedBytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)
    val huffmanDecodedBytes = FileEncodingTools.decode(huffmanEncodedBytes)
    Files.write(huffmanDecodedOutputFile, huffmanDecodedBytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)

    val shannonFanoOutputFile = dir.resolve("image.shannon_fano")
    val shannonFanoDecodedOutputFile = dir.resolve("image_shannon_fano.txt")
    val shannonFanoEncodedBytes = FileEncodingTools.encode(inputBytes, ShannonFanoEncoding.createCodesMap(inputBytes))
    Files.write(shannonFanoOutputFile, shannonFanoEncodedBytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)
    val shannonFanoDecodedBytes = FileEncodingTools.decode(shannonFanoEncodedBytes)
    Files.write(shannonFanoDecodedOutputFile, shannonFanoDecodedBytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)
}