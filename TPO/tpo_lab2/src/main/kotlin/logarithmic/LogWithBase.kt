package org.lolmaxlevel.logarithmic

class LogWithBase(
    private val ln: Ln = Ln()
) {
    fun calculate(x: Double, base: Double, epsilon: Double = 1e-10): Double {
        require(x > 0) { "Logarithm is undefined for non-positive values" }
        require(base > 0) { "Base must be positive" }
        require(base != 1.0) { "Base must not be equal to 1" }
        require(epsilon > 0) { "Epsilon must be positive" }

        if (x == 1.0) return 0.0
        if (x == base) return 1.0
        if (x == Double.POSITIVE_INFINITY) return Double.POSITIVE_INFINITY

        val ln1 = ln.calculate(x, epsilon)
        val lnBase = ln.calculate(base, epsilon)

        return ln1 / lnBase
    }
}