package task2

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.random.Random

class FibHeapTest {
    private lateinit var heap: FibHeap<Int>

    @BeforeEach
    fun setup() {
        heap = FibHeap()
    }

    @Test
    fun `empty heap should have no minimum`() {
        assertTrue(heap.isEmpty())
        assertNull(heap.findMin())
        assertNull(heap.extractMin())
    }

    @Test
    fun `insert should maintain heap property`() {
        val nodes = listOf(5, 3, 7, 1, 4).map { heap.insert(it) }
        assertFalse(heap.isEmpty())
        assertEquals(1, heap.findMin())
    }

    @Test
    fun `extract min should return elements in ascending order`() {
        listOf(5, 3, 7, 1, 4).forEach { heap.insert(it) }

        assertEquals(1, heap.extractMin())
        assertEquals(3, heap.extractMin())
        assertEquals(4, heap.extractMin())
        assertEquals(5, heap.extractMin())
        assertEquals(7, heap.extractMin())
        assertNull(heap.extractMin())
        assertTrue(heap.isEmpty())
    }

    @Test
    fun `decrease key should maintain heap property`() {
        val node1 = heap.insert(5)
        val node2 = heap.insert(3)
        val node3 = heap.insert(7)

        heap.decreaseKey(node3, 1)
        assertEquals(1, heap.findMin())

        heap.decreaseKey(node1, 2)
        assertEquals(1, heap.findMin())

        assertEquals(1, heap.extractMin())
        assertEquals(2, heap.extractMin())
        assertEquals(3, heap.extractMin())
    }

    @Test
    fun `union should merge heaps correctly`() {
        val heap1 = FibHeap<Int>()
        val heap2 = FibHeap<Int>()

        listOf(5, 3, 7).forEach { heap1.insert(it) }
        listOf(6, 2, 4).forEach { heap2.insert(it) }

        val merged = heap1.union(heap2)

        val extracted = mutableListOf<Int>()
        while (!merged.isEmpty()) {
            extracted.add(merged.extractMin()!!)
        }

        assertEquals(listOf(2, 3, 4, 5, 6, 7), extracted)
    }

    @Test
    fun `decrease key should throw on invalid key`() {
        val node = heap.insert(5)
        assertThrows<IllegalArgumentException> {
            heap.decreaseKey(node, 6)
        }
    }

    @Test
    fun `union with empty heaps should work correctly`() {
        val emptyHeap1 = FibHeap<Int>()
        val emptyHeap2 = FibHeap<Int>()

        val merged1 = emptyHeap1.union(emptyHeap2)
        assertTrue(merged1.isEmpty())

        heap.insert(1)
        val merged2 = heap.union(emptyHeap1)
        assertEquals(1, merged2.findMin())

        val merged3 = emptyHeap1.union(heap)
        assertEquals(1, merged3.findMin())
    }

    @Test
    fun `test cascading cuts`() {
        val nodes = mutableListOf<FibHeap.Node<Int>>()
        for (i in 500 downTo 1 step 1) {
            nodes.add(heap.insert(i))
        }

        for (i in 1 until 500 step 2) {
            heap.decreaseKey(nodes[i], 0)
        }


        // Verify the minimum after cascading cuts
        assertEquals(0, heap.findMin())
    }

    @Test
    fun `test random operations sequence`() {
        val random = Random(42)
        val nodes = mutableListOf<FibHeap.Node<Int>>()
        val valueMap = mutableMapOf<FibHeap.Node<Int>, Int>()

        // Insert random elements
        repeat(50) {
            val value = random.nextInt(1000)
            val node = heap.insert(value)
            nodes.add(node)
            valueMap[node] = value
        }

        // Random decrease key operations
        repeat(20) {
            val node = nodes.random(random)
            val oldValue = valueMap[node]!!
            val newValue = random.nextInt(oldValue)
            heap.decreaseKey(node, newValue)
            valueMap[node] = newValue
        }

        // Extract and verify order
        val extracted = mutableListOf<Int>()
        while (!heap.isEmpty()) {
            extracted.add(heap.extractMin()!!)
        }

        // First verify the heap property
        assertTrue(extracted.zipWithNext { a, b -> a <= b }.all { it })

        // Then verify we have all the correct values
        assertEquals(valueMap.values.sorted(), extracted)
    }

    @Test
    fun `test multiple consecutive unions`() {
        val heaps = List(5) { FibHeap<Int>() }

        // Insert different ranges in each heap
        heaps.forEachIndexed { index, heap ->
            (index * 10 until (index + 1) * 10)
                .forEach { heap.insert(it) }
        }

        // Consecutively union all heaps
        var result = heaps[0]
        for (i in 1 until heaps.size) {
            result = result.union(heaps[i])
        }

        // Verify all elements are present and in order
        val extracted = mutableListOf<Int>()
        while (!result.isEmpty()) {
            extracted.add(result.extractMin()!!)
        }

        assertEquals((0 until 50).toList(), extracted)
    }

    @Test
    fun `test node marking operations`() {
        val heap = FibHeap<Int>()

        // Test initial state
        val node = heap.insert(5)
        assertFalse(node.marked)

        // Test marking
        node.marked = true
        assertTrue(node.marked)

        // Test unmarking
        node.marked = false
        assertFalse(node.marked)

        // Test marking through cascading cuts
        val parent = heap.insert(4)
        val child = heap.insert(3)
        heap.decreaseKey(child, 2)
        assertFalse(parent.marked)
    }

    @Test
    fun `test decrease key with cascading cuts`() {
        val heap = FibHeap<Int>()


        val node4 = heap.insert(4)
        val node5 = heap.insert(5)

        heap.insert(1)
        heap.extractMin()

        // Decrease key of node5 to 1, which should trigger cuts
        heap.decreaseKey(node5, 1)

        // Verify the results
        assertEquals(1, heap.findMin()) // New minimum should be 1
//        assertNull(node4.child) // node5 should be cut from node4
        assertNull(node5.parent) // node5 should have no parent
        assertFalse(node5.marked) // node5 should be unmarked
        assertEquals(0, node4.degree) // node4 should have degree 0
    }

    @Test
    fun `test collection structure`() {
        val heap = FibHeap<Int>()
        val nodes = (1..5).map { heap.insert(it) }
        // trigger consolidation
        heap.extractMin()
        // Verify the structure
        //       2
        //      / \
        //     4   3
        //    /
        //   5
        assertNull(nodes[1].parent)
        assertEquals(nodes[1].child, nodes[2])
        assertEquals(nodes[2].parent, nodes[1])
        assertNull(nodes[2].child)
        assertEquals(nodes[3].parent, nodes[1])
        assertEquals(nodes[3].child, nodes[4])
        assertEquals(nodes[4].parent, nodes[3])
        assertNull(nodes[4].child)
    }
}

