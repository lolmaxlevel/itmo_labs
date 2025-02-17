package task1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.PI
import kotlin.math.sin

class SinTest {
    private val mySin = Sin()
    private val delta = 1e-10

    @Test
    fun testZero() {
        assertEquals(0.0, mySin.calculateSin(0.0), delta)
    }

    @Test
    fun testPiDivided2() {
        assertEquals(1.0, mySin.calculateSin(PI/2), delta)
    }

    @Test
    fun testPi() {
        assertEquals(0.0, mySin.calculateSin(PI), delta)
    }

    @Test
    fun testNegativePiDivided2() {
        assertEquals(-1.0, mySin.calculateSin(-PI/2), delta)
    }

    @Test
    fun testRandomValues() {
        val x = 0.5
        assertEquals(sin(x), mySin.calculateSin(x), delta)
    }

    @Test
    fun testLargeValue() {
        val x = 100.0
        assertEquals(sin(x), mySin.calculateSin(x), delta)
    }

    @Test
    fun testNegativeLargeValue() {
        val x = -100.0
        assertEquals(sin(x), mySin.calculateSin(x), delta)
    }

    @Test
    fun testNegativeRandomValues() {
        val x = -0.5
        assertEquals(sin(x), mySin.calculateSin(x), delta)
    }
}