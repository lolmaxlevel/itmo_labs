package org.lolmaxlevel.trigonometric

import kotlin.math.PI

class Cos {
    private val sin = Sin()

    fun calculateCos(x: Double, epsilon: Double = 1e-10): Double {
        // Cosine can be computed as sine with phase shift of π/2
        return sin.calculateSin(x + PI/2, epsilon)
    }
}