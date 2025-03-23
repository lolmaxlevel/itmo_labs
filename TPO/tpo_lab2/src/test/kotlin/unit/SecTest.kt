package unit

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.lolmaxlevel.trigonometric.Sec
import kotlin.math.PI
import kotlin.math.cos
import kotlin.test.assertEquals

class SecTest {
    private val sec = Sec(
        { x, _ -> cos(x) }
    )

    @ParameterizedTest
    @DisplayName("Test sec function")
    @ValueSource(doubles = [0.0, PI/6, PI/4, PI/3, PI, 2*PI])
    fun testSec(input: Double) {
        val expected = 1.0 / cos(input)
        val result = sec.calculate(input, 1e-10)
        assertEquals(expected, result, 1e-6)
    }

    @ParameterizedTest
    @DisplayName("Test sec illegal argument")
    @ValueSource(doubles = [PI/2, 3*PI/2])
    fun testSecIllegalArgument(input: Double) {
        assertThrows<IllegalArgumentException> {
           sec.calculate(input, 1e-10)
        }
    }
}