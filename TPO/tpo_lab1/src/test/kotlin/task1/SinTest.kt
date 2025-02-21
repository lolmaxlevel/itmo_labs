package task1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.math.PI
import kotlin.math.sin

class SinTest {
    private val mySin = Sin()
    private val delta = 1e-10

    @ParameterizedTest
    @ValueSource(doubles = [0.0, PI/2, -PI/2, PI, 100.0, -100.0, 0.5, -0.5, PI - 0.1, PI + 0.1, 3 * PI / 2 - 0.1])
    fun testZeroCommonPoints(x: Double) {
        assertEquals(sin(x), mySin.calculateSin(x), delta)
    }


    @ParameterizedTest
    @ValueSource(doubles = [100.0, -100.0])
    fun testLargeValue(x: Double) {
        assertEquals(sin(x), mySin.calculateSin(x), delta)
    }

    @ParameterizedTest
    @ValueSource(doubles = [PI/2, -PI/2, PI, 100.0, -100.0,])
    fun testPeriodicity(x: Double) {
        assertEquals(mySin.calculateSin(x), mySin.calculateSin(x + 2 * PI), delta)
    }

    @ParameterizedTest
    @ValueSource(doubles = [0.1, PI - 0.1, PI + 0.1, 3 * PI / 2 - 0.1])
    fun testUncommonPoints(x: Double) {
        assertEquals(sin(x), mySin.calculateSin(x), delta)
    }
}