package labs.lab3

import labs.lab1.ShannonEntropy
import kotlin.math.log2

fun main() {
    val msg = "Kohut Denys Anatolievych".replace(" ", "")
    val messageShannonEntropy = ShannonEntropy.calculate(msg.groupingBy { it }.eachCount().map { it.value.toDouble() / msg.length })
    println("Shannon Entropy = $messageShannonEntropy")
    val averageWord = -msg.groupingBy { it }.eachCount().map { it.value.toDouble() / msg.length }.sumOf { it * log2(it) }
    println("Lm = $averageWord")
    val huffmanTree = HuffmanEncoding.createTree(msg.toByteArray())
    val huffmanCodeMap = HuffmanEncoding.createCodesMap(huffmanTree)
    val huffmanEncoded = EncodingTools.encodeToString(msg, huffmanCodeMap)
    val huffmanDecoded = EncodingTools.decodeToString(huffmanEncoded, huffmanCodeMap)
    val huffmanInformationPerBit = EncodingTools.calculateInformationPerBits(msg, huffmanCodeMap)
    println("Huffman encoded = $huffmanEncoded")
    println("Huffman decoded = $huffmanDecoded")
    println("Huffman information per bit = $huffmanInformationPerBit")

    val shannonFanoCodeMap = ShannonFanoEncoding.createCodesMap(msg.toByteArray())
    val shannonFanoEncoded = EncodingTools.encodeToString(msg, shannonFanoCodeMap)
    val shannonFanoDecoded = EncodingTools.decodeToString(shannonFanoEncoded, shannonFanoCodeMap)
    val shannonFanoInformationPerBit = EncodingTools.calculateInformationPerBits(msg, shannonFanoCodeMap)
    println("Shanon-Fano encoded = $shannonFanoEncoded")
    println("Shanon-Fano decoded = $shannonFanoDecoded")
    println("Shanon-Fano information per bit = $shannonFanoInformationPerBit")
}