package unit
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.lolmaxlevel.trigonometric.Tan
import kotlin.math.PI
import kotlin.math.tan
import kotlin.test.assertEquals

class TanTest {
    private val tan = Tan()

    @ParameterizedTest
    @DisplayName("Test tan function")
    @ValueSource(doubles = [0.0, PI/6, PI/4, PI/3, 3*PI/4, 5*PI/4, 7*PI/4])
    fun testTan(input: Double) {
        val expected = tan(input)
        val result = tan.calculate(input)
        assertEquals(expected, result, 1e-6)
    }

    @ParameterizedTest
    @DisplayName("Test tan illegal argument")
    @ValueSource(doubles = [PI/2, 3*PI/2])
    fun testTanIllegalArgument(input: Double) {
        assertThrows<IllegalArgumentException> {
            tan.calculate(input)
        }
    }
}