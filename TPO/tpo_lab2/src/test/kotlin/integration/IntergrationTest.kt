package integration

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import org.lolmaxlevel.Function
import org.lolmaxlevel.logarithmic.Ln
import org.lolmaxlevel.logarithmic.LogWithBase
import org.lolmaxlevel.logarithmic.LogarithmicFunction
import org.lolmaxlevel.trigonometric.*
import kotlin.math.tan


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
        val result = trigonometricFunction.calculate(x, 1e-6)

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

    @ParameterizedTest(name = "Test full function with all mocks x = {0}, expected = {1}")
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
        "-2.41503, -1.54199",
        "-2.53352, 0.0",
        "-4.72, 0.0",
        "-4.2, -0.55681",
        "-0.3, -0.306592",
        "-1.0, 0.30814",
        "-2.0, -0.207881",
        "-2.57, 1.90263"
    )

    fun testFullFunctionAllMocks(x: Double, expected: Double) {
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

        var result = Function(trigonometricFunction, logorithmicFunction).calculate(x, 1e-6)

        if (result.isNaN()){
            result = Double.NEGATIVE_INFINITY
        }
        assertEquals(expected, result, 1e-2)
    }

    @ParameterizedTest(name = "Test full function on first level x = {0}, expected = {1}")
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
        "-2.41503, -1.54199",
        "-2.53352, 0.0",
        "-4.72, 0.0",
        "-4.2, -0.55681",
        "-0.3, -0.306592",
        "-1.0, 0.30814",
        "-2.0, -0.207881",
        "-2.57, 1.90263"
    )

    fun testFullFunctionFirstLevel(x: Double, expected: Double) {
        val sin = SinStub()
        val cos = CosStub()
        val cot = Cot()
        val csc = CscStub()
        val sec = Sec()
        val tan = Tan()

        val trigonometricFunction =
            TrigonometricFunction(sin = sin, cos = cos, cot = cot, csc = csc, sec = sec, tan = tan)

        val log2 = LogWithBase(Ln(), 2.0)
        val log5 = LogWithBase(Ln(), 5.0)
        val log10 = LogWithBase(Ln(), 10.0)
        val ln = Ln()

        val logarithmicFunction = LogarithmicFunction(log2 = log2, log5 = log5, log10 = log10, ln = ln)

        val result = Function(trigonometricFunction, logarithmicFunction).calculate(x, 1e-6)

        assertEquals(expected, result, 1e-1)
    }

    @ParameterizedTest(name = "Test full function on second level x = {0}, expected = {1}")
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
        "-2.41503, -1.54199",
        "-2.53352, 0.0",
        "-4.72, 0.0",
        "-4.2, -0.55681",
        "-0.3, -0.306592",
        "-1.0, 0.30814",
        "-2.0, -0.207881",
        "-2.57, 1.90263"
    )

    fun testFullFunctionSecondLevel(x: Double, expected: Double) {
        val sin = SinStub()
        val cos = Cos()
        val cot = Cot()
        val csc = Csc()
        val sec = Sec()
        val tan = Tan()

        val trigonometricFunction =
            TrigonometricFunction(sin = sin, cos = cos, cot = cot, csc = csc, sec = sec, tan = tan)

        val log2 = LogWithBase(Ln(), 2.0)
        val log5 = LogWithBase(Ln(), 5.0)
        val log10 = LogWithBase(Ln(), 10.0)
        val ln = Ln()

        val logarithmicFunction = LogarithmicFunction(log2 = log2, log5 = log5, log10 = log10, ln = ln)

        val result = Function(trigonometricFunction, logarithmicFunction).calculate(x, 1e-6)

        assertEquals(expected, result, 1e-2)
    }

    @ParameterizedTest(name = "Test full function on third level x = {0}, expected = {1}")
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
        "-2.41503, -1.54199",
        "-2.53352, 0.0",
        "-4.72, 0.0",
        "-4.2, -0.55681",
        "-0.3, -0.306592",
        "-1.0, 0.30814",
        "-2.0, -0.207881",
        "-2.57, 1.90263"
    )

    fun testFullFunctionThirdLevel(x: Double, expected: Double) {
        val sin = Sin()
        val cos = Cos()
        val cot = Cot()
        val csc = Csc()
        val sec = Sec()
        val tan = Tan()

        val trigonometricFunction =
            TrigonometricFunction(sin = sin, cos = cos, cot = cot, csc = csc, sec = sec, tan = tan)

        val log2 = LogWithBase(Ln(), 2.0)
        val log5 = LogWithBase(Ln(), 5.0)
        val log10 = LogWithBase(Ln(), 10.0)
        val ln = Ln()

        val logarithmicFunction = LogarithmicFunction(log2 = log2, log5 = log5, log10 = log10, ln = ln)

        val result = Function(trigonometricFunction, logarithmicFunction).calculate(x, 1e-6)

        assertEquals(expected, result, 1e-2)
    }

    ///// LOGARITHMIC FUNCTIONS

    @ParameterizedTest(name = "Test full function on fist level logaritmic x = {0}, expected = {1}")
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
        "-2.41503, -1.54199",
        "-2.53352, 0.0",
        "-4.72, 0.0",
        "-4.2, -0.55681",
        "-0.3, -0.306592",
        "-1.0, 0.30814",
        "-2.0, -0.207881",
        "-2.57, 1.90263"
    )

    fun testFullFunctionFirstLevelLogarithmic(x: Double, expected: Double) {
        val sin = Sin()
        val cos = Cos()
        val cot = Cot()
        val csc = Csc()
        val sec = Sec()
        val tan = Tan()

        val trigonometricFunction =
            TrigonometricFunction(sin = sin, cos = cos, cot = cot, csc = csc, sec = sec, tan = tan)

        val log2 = Log2Stub()
        val log5 = Log5Stub()
        val log10 = Log10Stub()
        val ln = LnStub()

        val logarithmicFunction = LogarithmicFunction(log2 = log2, log5 = log5, log10 = log10, ln = ln)

        val result = Function(trigonometricFunction, logarithmicFunction).calculate(x, 1e-6)

        assertEquals(expected, result, 1e-2)
    }

    @ParameterizedTest(name = "Test full function on second level logarithmic x = {0}, expected = {1}")
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
        "-2.41503, -1.54199",
        "-2.53352, 0.0",
        "-4.72, 0.0",
        "-4.2, -0.55681",
        "-0.3, -0.306592",
        "-1.0, 0.30814",
        "-2.0, -0.207881",
        "-2.57, 1.90263"
    )

    fun testFullFunctionSecondLevelLogarithmic(x: Double, expected: Double) {
        val sin = Sin()
        val cos = Cos()
        val cot = Cot()
        val csc = Csc()
        val sec = Sec()
        val tan = Tan()

        val trigonometricFunction =
            TrigonometricFunction(sin = sin, cos = cos, cot = cot, csc = csc, sec = sec, tan = tan)

        val log2 = LogWithBase(Ln(), 2.0)
        val log5 = LogWithBase(Ln(), 5.0)
        val log10 = LogWithBase(Ln(), 10.0)
        val ln = LnStub()

        val logarithmicFunction = LogarithmicFunction(log2 = log2, log5 = log5, log10 = log10, ln = ln)

        val result = Function(trigonometricFunction, logarithmicFunction).calculate(x, 1e-6)

        assertEquals(expected, result, 1e-2)
    }
}