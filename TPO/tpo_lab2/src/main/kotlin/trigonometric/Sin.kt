package org.lolmaxlevel.trigonometric

import kotlin.math.PI
import kotlin.math.abs

class Sin: TrigFunc {
    override fun calculate(x: Double, epsilon: Double): Double {
        require( epsilon > 0 ) { "epsilon must be greater than 0" }

        // Приводим x к диапазону [-π, π]
        var normalizedX = x % (2 * PI)
        if (normalizedX > PI) normalizedX -= 2 * PI
        if (normalizedX < -PI) normalizedX += 2 * PI

        var result = 0.0
        var term = normalizedX
        var n = 1

        // Вычисляем сумму ряда Тейлора до получения нужной по точности суммы
        // Сумма ряда Тейлора sin(x) = x - x^3/3! + x^5/5! - x^7/7! + ...
        while (abs(term) > epsilon) {
            result += term
            term = -term * normalizedX * normalizedX / ((2 * n) * (2 * n + 1))
            n++
        }

        return result
    }
}