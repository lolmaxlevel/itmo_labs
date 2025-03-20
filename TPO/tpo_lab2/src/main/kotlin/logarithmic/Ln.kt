package org.lolmaxlevel.logarithmic

import kotlin.math.abs

class Ln {
    //  ln(NaN) is NaN
    //  ln(x) is NaN when x < 0.0
    //  ln(+Inf) is +Inf
    //  ln(0.0) is -Inf

    fun calculate(x: Double, epsilon: Double = 1e-10): Double {
        require(x > 0) { "Natural logarithm is undefined for non-positive values" }
        require(epsilon > 0) { "Epsilon must be positive" }

        if (x == 1.0) return 0.0
        if (x == Double.POSITIVE_INFINITY) return Double.POSITIVE_INFINITY

        val z = abs((x - 1) / (x + 1))
        var sum = 0.0
        var m = z
        var iter = 1

        while (m > epsilon) {
            sum += m
            m *= z * z * (2 * iter - 1) / (2 * iter + 1)
            iter++
        }

        return (if (x < 1) -2 else 2) * sum
    }
}