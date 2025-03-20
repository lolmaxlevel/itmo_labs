package integration

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.lolmaxlevel.trigonometric.TrigonometricFunction


class IntegrationTest {

    @ParameterizedTest(name = "Test trigonometric function with x = {0}, expected = {1}")
    @CsvSource(
        "0.5, 7.011741",
        "1.565, 0.0",
        "2.2, -1.44127287",
    )
    fun testTrigonometricFunctionWithTableValues(x: Double, expected: Double) {
        val sin = SinStub()
        val cos = CosStub()
        val cot = CotStub()
        val csc = CscStub()
        val sec = SecStub()
        val tan = TanStub()

        val trigonometricFunction = TrigonometricFunction(sin=sin, cos=cos, cot=cot, csc=csc, sec=sec, tan=tan)
        val result = trigonometricFunction.calculate(x)

        assertEquals(expected, result, 1e-6, "Trigonometric function failed for x = $x")
    }
}