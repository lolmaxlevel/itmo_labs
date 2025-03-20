package org.lolmaxlevel.trigonometric

interface TrigFunc {
    fun calculate(x: Double, epsilon: Double = 1e-10): Double
}