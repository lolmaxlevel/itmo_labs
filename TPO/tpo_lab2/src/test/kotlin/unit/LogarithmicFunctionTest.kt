package unit

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import org.lolmaxlevel.logarithmic.LogarithmicFunction
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LogarithmicFunctionTest {
    private val logFunction = LogarithmicFunction()

    @ParameterizedTest
    @DisplayName("Test logarithmic function at critical points")
    @CsvSource(
        "0.5567182, 0.0",
        "2.0, 3.123824",
        "5.0, 5.362042",
        "33.38621, 10",
        "10.0, 7.05519"
    )
    fun testLogarithmicFunction(input: Double, expected: Double) {
        val result = logFunction.calculate(input)
        assertEquals(expected, result, 1e-6)
    }

    @Test
    @DisplayName("Test illegal argument exception")
    fun testIllegalArgumentException() {
        assertThrows<IllegalArgumentException> {
            logFunction.calculate(-1.0)
        }
    }

    @ParameterizedTest
    @DisplayName("Test logarithmic function at funny points")
    @ValueSource(doubles = [1.0])
    fun testLogarithmicFunctionFunny(input: Double) {
        val result = logFunction.calculate(input)
        assertTrue { result.isNaN() }
    }

}