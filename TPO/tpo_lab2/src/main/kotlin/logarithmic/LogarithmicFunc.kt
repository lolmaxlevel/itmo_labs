package org.lolmaxlevel.logarithmic

interface LogarithmicFunc {
    fun calculate(x: Double, epsilon: Double = 1e-10): Double
}