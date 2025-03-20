package integration

import org.lolmaxlevel.Utils
import org.lolmaxlevel.logarithmic.LogarithmicFunc
import org.lolmaxlevel.trigonometric.TrigFunc
import kotlin.math.PI
import kotlin.math.abs

class SinStub : TrigFunc {
    val name = "Sin"
    private val csvFileName = "sin.csv"

    override fun calculate(x: Double, epsilon: Double): Double {
        val normalizedX = x % (2 * PI)
        val tableValues = Utils().loadFromCsv(csvFileName)
        return tableValues.entries.minByOrNull { abs(it.key - normalizedX) }?.value ?: 0.0
    }

}
class CosStub : TrigFunc {
    val name = "Cos"
    private val csvFileName = "cos.csv"

    override fun calculate(x: Double, epsilon: Double): Double {
        val normalizedX = x % (2 * PI)
        val tableValues = Utils().loadFromCsv(csvFileName)
        return tableValues.entries.minByOrNull { abs(it.key - normalizedX) }?.value ?: 0.0
    }
}

class TanStub : TrigFunc {
    val name = "Tan"
    private val csvFileName = "tan.csv"

    override fun calculate(x: Double, epsilon: Double): Double {
        val normalizedX = x % PI
        val tableValues = Utils().loadFromCsv(csvFileName)
        return tableValues.entries.minByOrNull { abs(it.key - normalizedX) }?.value ?: 0.0
    }
}

class CotStub : TrigFunc {
    val name = "Cot"
    private val csvFileName = "cot.csv"

    override fun calculate(x: Double, epsilon: Double): Double {
        val normalizedX = x % PI
        val tableValues = Utils().loadFromCsv(csvFileName)
        return tableValues.entries.minByOrNull { abs(it.key - normalizedX) }?.value ?: 0.0
    }
}

class SecStub : TrigFunc {
    val name = "Sec"
    private val csvFileName = "sec.csv"

    override fun calculate(x: Double, epsilon: Double): Double {
        val normalizedX = x % (2 * PI)
        val tableValues = Utils().loadFromCsv(csvFileName)
        return tableValues.entries.minByOrNull { abs(it.key - normalizedX) }?.value ?: 0.0
    }
}

class CscStub : TrigFunc {
    val name = "Csc"
    private val csvFileName = "csc.csv"

    override fun calculate(x: Double, epsilon: Double): Double {
        val normalizedX = x % (2 * PI)
        val tableValues = Utils().loadFromCsv(csvFileName)
        return tableValues.entries.minByOrNull { abs(it.key - normalizedX) }?.value ?: 0.0
    }
}

class LnStub : LogarithmicFunc {
    val name = "Ln"
    private val csvFileName = "ln.csv"

    override fun calculate(x: Double, epsilon: Double): Double {
        val tableValues = Utils().loadFromCsv(csvFileName)
        return tableValues.entries.minByOrNull { abs(it.key - x) }?.value ?: 0.0
    }
}

class Log2Stub : LogarithmicFunc {
    val name = "Log2"
    private val csvFileName = "log2.csv"

    override fun calculate(x: Double, epsilon: Double): Double {
        val tableValues = Utils().loadFromCsv(csvFileName)
        return tableValues.entries.minByOrNull { abs(it.key - x) }?.value ?: 0.0
    }
}

class Log5Stub : LogarithmicFunc {
    val name = "Log5"
    private val csvFileName = "log5.csv"

    override fun calculate(x: Double, epsilon: Double): Double {
        val tableValues = Utils().loadFromCsv(csvFileName)
        return tableValues.entries.minByOrNull { abs(it.key - x) }?.value ?: 0.0
    }
}

class Log10Stub : LogarithmicFunc {
    val name = "Log10"
    private val csvFileName = "log10.csv"

    override fun calculate(x: Double, epsilon: Double): Double {
        val tableValues = Utils().loadFromCsv(csvFileName)
        return tableValues.entries.minByOrNull { abs(it.key - x) }?.value ?: 0.0
    }
}