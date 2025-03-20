package org.lolmaxlevel.trigonometric

class Cot(
    private val sin: TrigFunc = Sin(),
    private val cos: TrigFunc = Cos(sin)
) : TrigFunc {

    override fun calculate(x: Double, epsilon: Double): Double {
        require(epsilon > 0) { "epsilon must be greater than 0" }
        require(x % (Math.PI) != 0.0) { "x must not be a multiple of PI" }

        // cot = cos(x) / sin(x)
        return cos.calculate(x, epsilon) / sin.calculate(x, epsilon)
    }
}