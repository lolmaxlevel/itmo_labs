package unit

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.lolmaxlevel.trigonometric.Sin
import kotlin.math.PI
import kotlin.math.sin
import kotlin.test.assertEquals

class SinTest {
    private val mySin = Sin()

    @ParameterizedTest
    @DisplayName("Test sin function at critical points")
    @ValueSource(doubles = [0.0, PI/6, PI/4, PI/3, PI/2, PI, 3*PI/2, 2*PI])
    fun testSinAtCriticalPoints(input: Double) {
        val expected = sin(input)
        val result = mySin.calculate(input, 1e-10)
        assertEquals(expected, result, 1e-10)
    }
}