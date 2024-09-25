package labs.lab3

object ShannonFanoEncoding {
    fun createCodesMap(bytes: ByteArray): Map<Byte, String> {
        val map = mutableMapOf<Byte, String>()
        val prob = bytes.toList().groupingBy { it }.eachCount().mapValues { it.value.toDouble() / bytes.size }.toList()
        createCodesRecursively(prob, "", map)
        return map
    }

    private fun createCodesRecursively(input: List<Pair<Byte, Double>>, code: String, map: MutableMap<Byte, String>) {
        val list = input.toMutableList()
        list.sortByDescending { it.second }
        if (list.size == 1) {
            map[list[0].first] = code
            return
        } else if (list.size == 2) {
            map[list[0].first] = code + "0"
            map[list[1].first] = code + "1"
            return
        }
        val first = mutableListOf<Pair<Byte, Double>>()
        val second = mutableListOf<Pair<Byte, Double>>()
        var p1 = 0.0
        var p2 = 0.0
        while (list.isNotEmpty()) {
            if (p1 <= p2) {
                val n = list.removeFirst()
                p1 += n.second
                first.add(n)
            } else {
                val n = list.removeLast()
                p2 += n.second
                second.add(n)
            }
        }
        createCodesRecursively(first, code + "0", map)
        createCodesRecursively(second, code + "1", map)
    }


}