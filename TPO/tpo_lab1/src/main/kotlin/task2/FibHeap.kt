package task2

class FibHeap<T : Comparable<T>> {
    class Node<T : Comparable<T>>(var key: T) {
        var left: Node<T> = this
        var right: Node<T>? = this
        var parent: Node<T>? = null
        var child: Node<T>? = null
        var degree: Int = 0
        var marked: Boolean = false

        fun addToList(node: Node<T>) {
            node.right = this.right
            node.left = this
            this.right!!.left = node
            this.right = node
        }

        fun removeFromList() {
            this.left.right = this.right
            this.right!!.left = this.left
            this.left = this
            this.right = this
        }
    }

    private var min: Node<T>? = null
    private var size: Int = 0

    fun isEmpty() = min == null

    fun insert(key: T): Node<T> {
        val node = Node(key)
        if (min == null) {
            min = node
        } else {
            min!!.addToList(node)
            if (node.key < min!!.key) min = node
        }
        size++
        return node
    }

    fun findMin(): T? = min?.key

    fun extractMin(): T? {
        val minNode = min ?: return null

        // Перемещаем детей minNode в корневой список
        minNode.child?.let { child ->
            var current = child
            do {
                val next = current.right
                min!!.addToList(current)
                current.parent = null
                current = next!!
            } while (current != child)
        }

        // Удаляем minNode из корневого списка
        if (minNode == minNode.right) {
            min = null
        } else {
            min = minNode.right
            minNode.removeFromList()
            consolidate()
        }

        size--
        return minNode.key
    }

    private fun consolidate() {
        val maxDegree = (kotlin.math.log2(size.toFloat()) * 2).toInt() + 1
        val degreeArray = arrayOfNulls<Node<T>>(maxDegree)

        // Собираем все корни
        val roots = mutableListOf<Node<T>>()
        var current = min!!

        do {
            roots.add(current)
            current = current.right!!
        } while (current != min)

        // Объединяем деревья с одинаковой степенью
        for (root in roots) {
            var x = root
            var d = x.degree

            while (degreeArray[d] != null) {
                var y = degreeArray[d]!!
                if (x.key > y.key) {
                    val temp = x
                    x = y
                    y = temp
                }
                link(y, x)
                degreeArray[d] = null
                d++
            }
            degreeArray[d] = x
        }

        // Восстанавливаем корневой список и находим новый min
        min = null
        for (node in degreeArray) {
            node?.let {
                if (min == null) {
                    min = node
                    node.left = node
                    node.right = node
                } else {
                    min!!.addToList(node)
                    if (node.key < min!!.key) min = node
                }
            }
        }
    }

    private fun link(child: Node<T>, parent: Node<T>) {
        child.removeFromList()
        parent.child?.let {
            it.addToList(child)
        } ?: run {
            parent.child = child
            child.right = child
            child.left = child
        }
        child.parent = parent
        parent.degree++
        child.marked = false
    }

    fun decreaseKey(node: Node<T>, newKey: T) {
        require(newKey <= node.key) { "New key must be less than current key" }
        node.key = newKey
        val parent = node.parent

        if (parent != null) {
            cut(node, parent)
        }
        if (node.key < min!!.key) min = node
    }

    private fun cut(child: Node<T>, parent: Node<T>) {
        parent.child = child.right
        child.removeFromList()
        parent.degree--
        child.parent = null
        child.marked = false
    }

    fun union(other: FibHeap<T>): FibHeap<T> {
        val result = FibHeap<T>()
        when {
            this.min == null -> result.min = other.min
            other.min == null -> result.min = this.min
            else -> {
                val thisRight = this.min!!.right
                val otherLeft = other.min!!.left

                this.min!!.right = other.min
                other.min!!.left = this.min!!
                otherLeft.right = thisRight

                result.min = if (this.min!!.key < other.min!!.key) this.min else other.min
            }
        }
        result.size = this.size + other.size
        return result
    }
}