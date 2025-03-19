package org.lolmaxlevel.trigonometric

class Cot {
    private val sin = Sin()
    private val cos = Cos()

    fun calculateCot(x: Double, epsilon: Double = 1e-10): Double {
        // Cotangent can be computed as cosine divided by sine
        return cos.calculateCos(x, epsilon) / sin.calculateSin(x, epsilon)
    }
}