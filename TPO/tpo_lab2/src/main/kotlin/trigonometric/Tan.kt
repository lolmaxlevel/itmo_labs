package org.lolmaxlevel.trigonometric

class Tan {
    private val sin = Sin()
    private val cos = Cos()

    fun calculateTan(x: Double, epsilon: Double = 1e-10): Double {
        // Tangent can be computed as sine divided by cosine
        return sin.calculateSin(x, epsilon) / cos.calculateCos(x, epsilon)
    }
}