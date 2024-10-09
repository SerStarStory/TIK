package labs.lab3

object HuffmanEncoding {
    class HuffmanTreeNode(val value: Byte?, val frequency: Int, var left: HuffmanTreeNode?, var right: HuffmanTreeNode?) {
        constructor(left: HuffmanTreeNode, right: HuffmanTreeNode):
                this(null, left.frequency + right.frequency, left, right)

        override fun toString(): String {
            return "HuffmanTreeNode(value=$value, frequency=$frequency, left=$left, right=$right)"
        }
    }

    fun createTree(bytes: ByteArray): HuffmanTreeNode {
        val nodes = bytes.groupBy { it }.map { HuffmanTreeNode(it.key, it.value.size, null, null) }.toMutableList()
        while (nodes.size > 1) {
            nodes.sortBy { it.frequency }
            val left = nodes.removeFirst()
            val right = nodes.removeFirst()
            val node = HuffmanTreeNode(left, right)
            nodes.add(node)
        }
        return nodes[0]
    }

    fun createTree(bytes: ByteArray, frequency: DoubleArray): HuffmanTreeNode {
        val nodes = bytes.groupBy { it }.map { HuffmanTreeNode(it.key, (frequency[it.key.toInt()] * 100).toInt(), null, null) }.toMutableList()
        while (nodes.size > 1) {
            nodes.sortBy { it.frequency }
            val left = nodes.removeFirst()
            val right = nodes.removeFirst()
            val node = HuffmanTreeNode(left, right)
            nodes.add(node)
        }
        return nodes[0]
    }

    fun createCodesMap(bytes: ByteArray): Map<Byte, String> {
        return createCodesMap(createTree(bytes))
    }

    fun createCodesMap(parent: HuffmanTreeNode): Map<Byte, String> {
        val map = mutableMapOf<Byte, String>()
        fillCodeMap(parent, "", map)
        return map
    }

    private fun fillCodeMap(node: HuffmanTreeNode?, code: String, map: MutableMap<Byte, String>) {
        // 0 - left, 1 - right
        node ?: return
        if (node.value != null) {
            map[node.value] = code
        } else {
            fillCodeMap(node.left, code + "0", map)
            fillCodeMap(node.right, code + "1", map)
        }
    }
}