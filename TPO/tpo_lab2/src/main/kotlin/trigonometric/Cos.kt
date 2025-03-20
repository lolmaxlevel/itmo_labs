package org.lolmaxlevel.trigonometric

import kotlin.math.PI

class Cos(private val sin: TrigFunc = Sin()) : TrigFunc {
    override fun calculate(x: Double, epsilon: Double): Double {
        require(x.isFinite()) { "x must be finite" }
        require(epsilon > 0) { "epsilon must be greater than 0" }

        // Cosine as sine with phase shift
        return sin.calculate(x + PI / 2, epsilon)
    }
}