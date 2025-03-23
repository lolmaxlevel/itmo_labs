package unit

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.lolmaxlevel.logarithmic.Ln
import kotlin.math.ln
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class LnTest {
    private val myLn = Ln()

    @ParameterizedTest
    @ValueSource(doubles = [1.0, 2.718281828459045, 0.5, 0.1])
    fun testLnAgainstStandardLibrary(input: Double) {
        assertEquals(ln(input), myLn.calculate(input, 1e-10), 1e-6)
    }


    @ParameterizedTest
    @ValueSource(doubles = [-1.0, -2.0, -3.0, -100.0])
    fun testLnWithNegativeInput(input: Double) {
        assertFailsWith<IllegalArgumentException> {
            myLn.calculate(input, 1e-10)
        }
    }

    @Test
    fun testLnWithZeroInput() {
        assertFailsWith<IllegalArgumentException> {
            myLn.calculate(0.0, 1e-10)
        }
    }
}