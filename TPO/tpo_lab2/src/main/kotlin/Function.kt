package org.lolmaxlevel

import org.lolmaxlevel.logarithmic.LogarithmicFunction
import org.lolmaxlevel.trigonometric.TrigonometricFunction

class Function(
    private val trigonometricFunction: TrigonometricFunction = TrigonometricFunction(),
    private val logarithmicFunction: LogarithmicFunction = LogarithmicFunction()
) {

    fun calculate(x: Double, epsilon: Double): Double {
        if (x <= 0) {
            return trigonometricFunction.calculate(x, epsilon)
        }
        return logarithmicFunction.calculate(x, epsilon)
    }
}