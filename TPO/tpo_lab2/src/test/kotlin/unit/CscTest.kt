package unit

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.lolmaxlevel.trigonometric.Csc
import kotlin.math.PI
import kotlin.math.sin
import kotlin.test.assertEquals

class CscTest {
    private val csc = Csc()

    @ParameterizedTest
    @DisplayName("Test csc function")
    @ValueSource(doubles = [PI/6, PI/4, PI/3, PI/2, 3*PI/2])
    fun testCsc(input: Double) {
        val expected = 1.0 / sin(input)
        val result = csc.calculate(input)
        assertEquals(expected, result, 1e-10)
    }

    @ParameterizedTest
    @DisplayName("Test illegal argument exception")
    @ValueSource(doubles = [0.0, PI, 2*PI, -PI])
    fun testIllegalArgumentException(input: Double) {
        assertThrows<IllegalArgumentException> { csc.calculate(input) }
    }
}