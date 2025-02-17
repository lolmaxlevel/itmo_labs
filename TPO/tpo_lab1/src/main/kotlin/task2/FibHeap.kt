package task2

class FibHeap<T : Comparable<T>> {
    class Node<T : Comparable<T>>(var key: T) {
        var left: Node<T> = this
        var right: Node<T> = this
        var parent: Node<T>? = null
        var child: Node<T>? = null
        var degree: Int = 0
        var marked: Boolean = false

        fun addToList(node: Node<T>) {
            node.right = this.right
            node.left = this
            this.right.left = node
            this.right = node
        }

        fun removeFromList() {
            left.right = right
            right.left = left
        }
    }

    private var min: Node<T>? = null
    private var size: Int = 0

    fun isEmpty() = min == null

    fun insert(key: T): Node<T> {
        val node = Node(key)
        min = if (min == null) {
            node
        } else {
            min!!.addToList(node)
            if (node.key < min!!.key) node else min
        }
        size++
        return node
    }

    fun findMin(): T? = min?.key

    fun extractMin(): T? {
        val minNode = min ?: return null

        // Handle children of min node
        minNode.child?.let { child ->
            var current = child
            do {
                val next = current.right
                min!!.addToList(current)
                current.parent = null
                current = next
            } while (current != child)
        }

        // Remove min from root list
        if (minNode === minNode.right) {
            min = null
        } else {
            val next = minNode.right
            minNode.removeFromList()
            min = next
            consolidate()
        }

        size--
        return minNode.key
    }

    fun union(other: FibHeap<T>): FibHeap<T> {
        val result = FibHeap<T>()
        result.min = this.min

        if (this.min == null) {
            result.min = other.min
        } else if (other.min != null) {
            val thisRight = this.min!!.right
            val otherLeft = other.min!!.left

            this.min!!.right = other.min!!
            other.min!!.left = this.min!!
            thisRight.left = otherLeft
            otherLeft.right = thisRight

            if (other.min!!.key < this.min!!.key) {
                result.min = other.min
            }
        }

        result.size = this.size + other.size
        return result
    }

    fun decreaseKey(node: Node<T>, newKey: T) {
        require(newKey <= node.key) { "New key must be less than current key" }

        node.key = newKey
        val parent = node.parent

        if (parent != null && node.key < parent.key) {
            cut(node, parent)
            cascadingCut(parent)
        }

        if (min == null || node.key < min!!.key) {
            min = node
        }
    }

    private fun cut(child: Node<T>, parent: Node<T>) {
        if (parent.child === child) {
            parent.child = if (child.right === child) null else child.right
        }
        child.removeFromList()
        parent.degree--

        min!!.addToList(child)
        child.parent = null
        child.marked = false
    }

    private fun cascadingCut(node: Node<T>) {
        val parent = node.parent
        if (parent != null) {
            if (!node.marked) {
                node.marked = true
            } else {
                cut(node, parent)
                cascadingCut(parent)
            }
        }
    }

    private fun consolidate() {
        val maxDegree = (kotlin.math.log2(size.toFloat()) * 2).toInt() + 1
        val degreeArray = arrayOfNulls<Node<T>>(maxDegree)

        // Collect all roots
        val roots = mutableListOf<Node<T>>()
        var current = min
        do {
            roots.add(current!!)
            current = current.right
        } while (current != min)

        // Process all roots
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

        // Rebuild root list
        min = null
        for (node in degreeArray) {
            if (node != null) {
                if (min == null) {
                    min = node
                    node.left = node
                    node.right = node
                } else {
                    min!!.addToList(node)
                    if (node.key < min!!.key) {
                        min = node
                    }
                }
            }
        }
    }

    private fun link(child: Node<T>, parent: Node<T>) {
        child.removeFromList()
        if (parent.child == null) {
            parent.child = child
            child.right = child
            child.left = child
        } else {
            parent.child!!.addToList(child)
        }
        child.parent = parent
        parent.degree++
        child.marked = false
    }
}