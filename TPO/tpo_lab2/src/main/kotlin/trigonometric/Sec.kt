package org.lolmaxlevel.trigonometric

class Sec {
    private val cos = Cos()

    fun calculateSec(x: Double, epsilon: Double = 1e-10): Double {
        // Secant can be computed as reciprocal of cosine
        return 1 / cos.calculateCos(x, epsilon)
    }
}