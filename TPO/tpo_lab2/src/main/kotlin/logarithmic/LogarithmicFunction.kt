package org.lolmaxlevel.logarithmic


class LogarithmicFunction(
    private val ln: LogarithmicFunc = Ln(),
    private val log2: LogarithmicFunc = LogWithBase(ln, 2.0),
    private val log5: LogarithmicFunc = LogWithBase(ln, 5.0),
    private val log10: LogarithmicFunc = LogWithBase(ln, 10.0)
) {
    // (((((log_2(x) / log_10(x)) / log_2(x)) * log_5(x)) + (log_10(x) + ln(x))) - (log_10(x) - log_2(x)))
    fun calculate(x: Double, epsilon: Double = 1e-10): Double {
        require(epsilon > 0) { "epsilon must be positive" }
        require(x > 0) { "x must be positive" }

        return (((((log2.calculate(x, epsilon) / log10.calculate(x, epsilon)) / log2.calculate(
            x, epsilon
        )) * log5.calculate(x, epsilon)) + (log10.calculate(x, epsilon) + ln.calculate(
            x, epsilon
        ))) - (log10.calculate(
            x, epsilon
        ) - log2.calculate(x, epsilon)))
    }
}