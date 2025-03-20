package org.lolmaxlevel.trigonometric

class Csc(private val sin: TrigFunc = Sin()) : TrigFunc {
    override fun calculate(x: Double, epsilon: Double): Double {
        require(epsilon > 0) { "epsilon must be greater than 0" }
        require(x % Math.PI != 0.0) { "x must not be a multiple of PI" }

        return 1 / sin.calculate(x, epsilon)
    }
}