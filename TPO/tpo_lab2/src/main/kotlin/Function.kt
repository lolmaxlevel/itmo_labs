package org.lolmaxlevel

import org.lolmaxlevel.logarithmic.LogarithmicFunction
import org.lolmaxlevel.trigonometric.TrigonometricFunction

class Function {

    fun calculate(x: Double, epsilon: Double): Double {
        if (x <= 0) {
            return TrigonometricFunction().calculate(x, epsilon)
        }
        return LogarithmicFunction().calculate(x, epsilon)
    }
}