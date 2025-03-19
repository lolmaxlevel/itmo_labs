package org.lolmaxlevel.trigonometric


object MyMath {
    fun sin(x: Double, epsilon: Double = 1e-10): Double = Sin().calculateSin(x, epsilon)
    fun cos(x: Double, epsilon: Double = 1e-10): Double = Cos().calculateCos(x, epsilon)
    fun tan(x: Double, epsilon: Double = 1e-10): Double = Tan().calculateTan(x, epsilon)
    fun cot(x: Double, epsilon: Double = 1e-10): Double = Cot().calculateCot(x, epsilon)
    fun sec(x: Double, epsilon: Double = 1e-10): Double = Sec().calculateSec(x, epsilon)
    fun csc(x: Double, epsilon: Double = 1e-10): Double = Csc().calculateCsc(x, epsilon)
}