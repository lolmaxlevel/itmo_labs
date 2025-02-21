package task3

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SurfaceTest {
    @Test
    fun `test enum values are unique`() {
        val values = Surface.entries.toTypedArray()
        val uniqueValues = values.toSet()
        assertEquals(values.size, uniqueValues.size)
    }
}