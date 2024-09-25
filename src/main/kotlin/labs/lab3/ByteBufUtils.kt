//package labs.lab3
//
//import java.io.EOFException
//import java.io.InputStream
//import java.io.OutputStream
//
//
//fun OutputStream.writeVarLong(value: Long) {
//    var x = value
//    while (x and 0x7f.inv() != 0L) {
//        write((x and 0x7f or 0x80).toInt())
//        x = x ushr 7
//    }
//    write(x.toInt())
//}
//
//fun InputStream.readVarLong(): Long {
//    var x: Long = read().toLong()
//    if (x >= 0) {
//        return x
//    }
//    x = x and 0x7f
//    var s = 7
//    while (true) {
//        var b: Long = read().toLong()
//        if (b < 0) {
//            throw EOFException()
//        }
//        b = b.toByte().toLong()
//        x = x or (b and 0x7f shl s)
//        if (b >= 0) {
//            return x
//        }
//        s += 7
//    }
//}
//
