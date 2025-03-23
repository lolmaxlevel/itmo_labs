package unit

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.lolmaxlevel.trigonometric.Cos
import org.lolmaxlevel.trigonometric.Sin
import org.lolmaxlevel.trigonometric.TrigFunc
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.test.assertEquals

class CosTest {
    private val cos = Cos(TrigFunc { x, _ -> sin(x) })

    @ParameterizedTest
    @DisplayName("Test cos function at critical points")
    @ValueSource(doubles = [0.0, PI / 6, PI / 4, PI / 3, PI / 2, PI, 3 * PI / 2, 2 * PI])
    fun testCosAtCriticalPoints(input: Double) {
        val expected = cos(input)
        val result = cos.calculate(input, 1e-10)
        assertEquals(expected, result, 1e-10)
    }
}