package unit

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.lolmaxlevel.trigonometric.Cot
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan
import kotlin.test.assertEquals

class CotTest {
    private val cot = Cot(
        { x, _ -> sin(x) },
        { x, _ -> cos(x) }
    )

    @ParameterizedTest
    @DisplayName("Test cot function")
    @ValueSource(doubles = [PI / 6, PI / 4, PI / 3, 3 * PI / 4, 5 * PI / 4, 7 * PI / 4])
    fun testCot(input: Double) {
        val expected = 1.0 / tan(input)
        val result = cot.calculate(input, 1e-10)
        assertEquals(expected, result, 1e-5)
    }

    @ParameterizedTest
    @DisplayName("Test cot function on asymptotes")
    @ValueSource(doubles = [0.0, PI, -PI])
    fun testCotOnAsymptotes(input: Double) {
        assertThrows<IllegalArgumentException> { cot.calculate(input, 1e-10) }
    }
}