package labs.lab3

import java.math.BigInteger
import java.util.*

object BitArraySimple {

    fun addString(str: String, bigInt: BigInteger): BigInteger {
        var bigInt = bigInt
        str.forEach {
            bigInt = bigInt.shiftLeft(1)
            if (it == '1') bigInt = bigInt.or(BigInteger.ONE)
        }
        return bigInt
    }

    fun read(big: BigInteger, n: Int): String {
        return if (big.testBit(n)) return "1" else "0"
    }
}