package org.lolmaxlevel.logarithmic

fun interface LogarithmicFunc {
    fun calculate(x: Double, epsilon: Double): Double
}