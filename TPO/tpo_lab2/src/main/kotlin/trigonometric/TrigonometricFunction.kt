package org.lolmaxlevel.trigonometric

import kotlin.math.pow

class TrigonometricFunction(
    private val sin: TrigFunc = Sin(),
    private val cos: TrigFunc = Cos(sin),
    private val tan: TrigFunc = Tan(sin, cos),
    private val cot: TrigFunc = Cot(sin, cos),
    private val sec: TrigFunc = Sec(cos),
    private val csc: TrigFunc = Csc(sin)
) : TrigFunc {
    override fun calculate(x: Double, epsilon: Double): Double {
        val sinX = sin.calculate(x, epsilon)
        val cosX = cos.calculate(x, epsilon)
        val tanX = tan.calculate(x, epsilon)
        val cotX = cot.calculate(x, epsilon)
        val secX = sec.calculate(x, epsilon)
        val cscX = csc.calculate(x, epsilon)

        return ((((((((cotX * tanX) + cotX) + secX) + secX)
                * (cscX - cotX)) * ((cotX * cotX) / secX))
                * cotX) - (((((cosX - sinX) * cosX) /
                (cscX / cosX)).pow(3)) * ((cosX + (cotX.pow(3)))
                / cscX)))
    }
}