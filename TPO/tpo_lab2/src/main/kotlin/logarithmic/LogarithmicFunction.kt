package org.lolmaxlevel.logarithmic


class LogarithmicFunction(
    private val ln: Ln = Ln(), private val log: LogWithBase = LogWithBase(ln)
) {
    // (((((log_2(x) / log_10(x)) / log_2(x)) * log_5(x)) + (log_10(x) + ln(x))) - (log_10(x) - log_2(x)))
    fun calculate(x: Double, epsilon: Double = 1e-10): Double {
        require(epsilon > 0) { "epsilon must be positive" }
        require(x > 0) { "x must be positive" }

        return (((((log.calculate(x, 2.0, epsilon) / log.calculate(x, 10.0, epsilon)) / log.calculate(
            x,
            2.0,
            epsilon
        )) * log.calculate(x, 5.0, epsilon)) + (log.calculate(x, 10.0, epsilon) + ln.calculate(
            x,
            epsilon
        ))) - (log.calculate(
            x, 10.0, epsilon
        ) - log.calculate(x, 2.0, epsilon)))
    }
}