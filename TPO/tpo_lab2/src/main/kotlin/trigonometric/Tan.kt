package org.lolmaxlevel.trigonometric

class Tan(
    private val sin: TrigFunc = Sin(),
    private val cos: TrigFunc = Cos(sin)
) :TrigFunc{
    override fun calculate(x: Double, epsilon: Double): Double {
        require(epsilon > 0) { "epsilon must be greater than 0" }
        require(x % Math.PI != Math.PI / 2) { "tan is not defined for x = PI/2 + k*PI" }

        return sin.calculate(x, epsilon) / cos.calculate(x, epsilon)
    }
}