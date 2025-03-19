package org.lolmaxlevel.trigonometric

class Csc {
    private val sin = Sin()

    fun calculateCsc(x: Double, epsilon: Double = 1e-10): Double {
        // Cosecant can be computed as reciprocal of sine
        return 1 / sin.calculateSin(x, epsilon)
    }
}