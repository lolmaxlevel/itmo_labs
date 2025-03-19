package org.lolmaxlevel.trigonometric

import org.lolmaxlevel.trigonometric.MyMath.cos
import org.lolmaxlevel.trigonometric.MyMath.sin
import org.lolmaxlevel.trigonometric.MyMath.cot
import org.lolmaxlevel.trigonometric.MyMath.sec
import org.lolmaxlevel.trigonometric.MyMath.csc
import org.lolmaxlevel.trigonometric.MyMath.tan
import kotlin.math.pow

///((((((((cot(x) * tan(x)) + cot(x)) + sec(x)) + sec(x)) * (csc(x) - cot(x))) * ((cot(x) ^ 2) / sec(x))) * cot(x))
// - (((((cos(x) - sin(x)) * cos(x)) / (csc(x) / cos(x))) ^ 3) * ((cos(x) + (cot(x) ^ 3)) / csc(x))))
class TrigonometricFunction {
    fun calculateTrigonometricFunction(x: Double, epsilon: Double = 1e-10): Double {
        return ((((((((cot(x, epsilon) * tan(x, epsilon)) + cot(x, epsilon)) + sec(x, epsilon)) + sec(x, epsilon))
                * (csc(x, epsilon) - cot(x, epsilon))) * ((cot(x, epsilon) * cot(x, epsilon)) / sec(x, epsilon)))
                * cot(x, epsilon)) - (((((cos(x, epsilon) - sin(x, epsilon)) * cos(x, epsilon)) /
                (csc(x, epsilon) / cos(x, epsilon))).pow(3)) * ((cos(x, epsilon) + (cot(x, epsilon).pow(3)))
                / csc(x, epsilon))))
    }
}