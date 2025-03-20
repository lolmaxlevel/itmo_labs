package org.lolmaxlevel.trigonometric

class Sec(private val cos: TrigFunc = Cos(Sin())) : TrigFunc {
    override fun calculate(x: Double, epsilon: Double): Double {
        require(epsilon > 0) { "epsilon must be greater than 0" }
        require(x % Math.PI == 0.0 || x == 0.0 || x % (Math.PI / 2) != 0.0) {
            "x must not be a multiple of PI/2"
        }

        return 1 / cos.calculate(x, epsilon)
    }
}