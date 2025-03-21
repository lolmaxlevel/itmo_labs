package integration

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.lolmaxlevel.Function
import org.lolmaxlevel.logarithmic.LogWithBase
import org.lolmaxlevel.logarithmic.LogarithmicFunction
import org.lolmaxlevel.trigonometric.*


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

        val trigonometricFunction =
            TrigonometricFunction(sin = sin, cos = cos, cot = cot, csc = csc, sec = sec, tan = tan)
        val result = trigonometricFunction.calculate(x)

        assertEquals(expected, result, 1e-6, "Trigonometric function failed for x = $x")
    }

    @ParameterizedTest(name = "Test logarithmic function with x = {0}, expected = {1}")
    @CsvSource(
        "2.0, 3.123824",
        "5.0, 5.362042",
        "10.0, 7.05519"
    )
    fun testLogarithmicFunction(input: Double, expected: Double) {
        val log2 = Log2Stub()
        val log5 = Log5Stub()
        val log10 = Log10Stub()
        val ln = LnStub()


        val logorithmicFunction = LogarithmicFunction(log2 = log2, log5 = log5, log10 = log10, ln = ln)
        val result = logorithmicFunction.calculate(input)
        kotlin.test.assertEquals(expected, result, 1e-6)
    }

    @ParameterizedTest(name = "Test full function with x = {0}, expected = {1}")
    @CsvSource(
        "0.55672, 0.0",
        "4.31123, 5.0",
        "2.0, 3.123824",
        "5.0, 5.362042",
        "10.0, 7.05519",
        "-0.3049727, 0.0",
        "-1.57, 0.0",
        "-1.0, 0.308036",
        "-0.436438, 2.476534",
        "-2.41503, -1.54199"
    )
    fun testFullFunction(x: Double, expected: Double) {
        val sin = SinStub()
        val cos = CosStub()
        val cot = CotStub()
        val csc = CscStub()
        val sec = SecStub()
        val tan = TanStub()

        val trigonometricFunction =
            TrigonometricFunction(sin = sin, cos = cos, cot = cot, csc = csc, sec = sec, tan = tan)

        val log2 = Log2Stub()
        val log5 = Log5Stub()
        val log10 = Log10Stub()
        val ln = LnStub()

        val logorithmicFunction = LogarithmicFunction(log2 = log2, log5 = log5, log10 = log10, ln = ln)

        val result = Function(trigonometricFunction, logorithmicFunction).calculate(x, 1e-6)

        assertEquals(expected, result, 1e-2)
    }

    @ParameterizedTest(name = "Test cos with stub sin x = {0}, expected = {1}")
    @CsvSource(
        "0.0, 1.0",
        "1.5707963268, 0",
        "1.0, 0.540302",
        "2.0, -0.4161468365",
        "3.1415926536, -1"
    )
    fun testCosWithSinStub(x: Double, expected: Double) {
        val sin = SinStub()

        val result = Cos(sin).calculate(x)
        assertEquals(expected, result, 1e-3, "Trigonometric function failed for x = $x")
    }

    @ParameterizedTest(name = "Test tan with stubs x = {0}, expected = {1}")
    @CsvSource(
        "0.0, 0.0",
        "0.7853981634, 1.0",          // π/4
        "2.3561944902, -1.0",         // 3π/4
        "3.1415926536, 0.0",          // π
        "3.9269908170, 1.0"           // 5π/4
    )
    fun testTanWithStubs(x: Double, expected: Double) {
        val sin = SinStub()
        val cos = CosStub()
        val result = Tan(sin, cos).calculate(x, 1e-10)
        assertEquals(expected, result, 1e-3, "Tan function failed for x = $x")
    }


    @ParameterizedTest(name = "Test cot with stubs x = {0}, expected = {1}")
    @CsvSource(
        "0.7853981634, 1.0",          // π/4
        "2.3561944902, -1.0",         // 3π/4
        "3.9269908170, 1.0",          // 5π/4
        "5.4977871438, -1.0"          // 7π/4
    )
    fun testCotWithStubs(x: Double, expected: Double) {
        val sin = SinStub()
        val cos = CosStub()
        val result = Cot(sin, cos).calculate(x, 1e-10)
        assertEquals(expected, result, 1e-3, "Cot function failed for x = $x")
    }

    @ParameterizedTest(name = "Test sec with stub cos x = {0}, expected = {1}")
    @CsvSource(
        "0.0, 1.0",
        "1.0, 1.85081",
        "3.1415926536, -1.0",         // π
        "6.2831853072, 1.0"           // 2π
    )
    fun testSecWithCosStub(x: Double, expected: Double) {
        val cos = CosStub()
        val result = Sec(cos).calculate(x, 1e-10)
        assertEquals(expected, result, 1e-3, "Sec function failed for x = $x")
    }

    @ParameterizedTest(name = "Test csc with stub sin x = {0}, expected = {1}")
    @CsvSource(
        "0.5235987756, 2.0",          // π/6
        "1.5707963268, 1.0",          // π/2
        "4.7123889804, -1.0"          // 3π/2
    )
    fun testCscWithSinStub(x: Double, expected: Double) {
        val sin = SinStub()
        val result = Csc(sin).calculate(x, 1e-10)
        assertEquals(expected, result, 1e-2, "Csc function failed for x = $x")
    }


    @ParameterizedTest(name = "Test LogWithBase with x = {0}, base = {1}, expected = {2}")
    @CsvSource(
        "1.0, 3.0, 0.0",
        "3.0, 3.0, 1.0",
        "9.0, 3.0, 2.0",
        "0.33333, 3.0, -1.0"
    )
    fun testLogWithBase(x: Double, base: Double, expected: Double) {
        val ln = LnStub()
        val logWithBase = LogWithBase(ln, base)
        val result = logWithBase.calculate(x, 1e-10)
        assertEquals(expected, result, 1e-3, "LogWithBase function failed for x = $x, base = $base")
    }
}