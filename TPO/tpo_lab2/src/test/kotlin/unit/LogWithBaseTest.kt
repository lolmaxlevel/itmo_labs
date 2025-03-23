package unit

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.lolmaxlevel.logarithmic.Ln
import org.lolmaxlevel.logarithmic.LogWithBase
import kotlin.math.ln
import kotlin.test.assertEquals

class LogWithBaseTest {
    private val ln = Ln()

    @ParameterizedTest
    @DisplayName("Test logarithm with various bases")
    @CsvSource(
        "10.0, 10.0, 1.0",
        "100.0, 10.0, 2.0",
        "8.0, 2.0, 3.0",
        "16.0, 2.0, 4.0",
        "1.0, 5.0, 0.0",
        "125.0, 5.0, 3.0",
        "0.1, 10.0, -1.0",
        "0.5, 2.0, -1.0"
    )
    fun testLogWithBase(value: Double, base: Double, expected: Double) {
        val logWithBase = LogWithBase(ln, base)
        val result = logWithBase.calculate(value, 1e-10)
        assertEquals(expected, result, 1e-6)

        // Verify against standard library using change of base formula
        val standardResult = ln(value) / ln(base)
        assertEquals(standardResult, result, 1e-6)
    }
}