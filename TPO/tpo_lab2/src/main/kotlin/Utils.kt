package org.lolmaxlevel

import java.io.InputStreamReader

class Utils {
    fun createCsvFile(
        fileName: String, functionName: String, start: Double, end: Double,
        step: Double, epsilon: Double, function: (Double, Double) -> Double
    ) {
        val file = java.io.File(fileName)
        file.writeText("x,$functionName\n")

        var x = start
        while (x <= end) {
            try {
                val result = function(x, epsilon)
                file.appendText("$x,$result\n")
            } catch (e: Exception) {
                file.appendText("$x,ERROR: ${e.message}\n")
            }
            x += step
        }
    }

    fun loadFromCsv(csvFileName: String): Map<Double, Double> {
        fun parseKey(str: String): Double {
            return str.toDouble()
        }

        val result = mutableMapOf<Double, Double>()
        val resourceStream = javaClass.classLoader.getResourceAsStream(csvFileName)
            ?: throw IllegalStateException("CSV file not found: $csvFileName")

        InputStreamReader(resourceStream).use { reader ->
            reader.buffered().readLines()
                .drop(1) // Skip header
                .forEach { line ->
                    val parts = line.split(",")
                    if (parts.size >= 2) {
                        val xStr = parts[0].trim()
                        val value = parts[1].trim().toDoubleOrNull()

                        if (value != null) {
                            val key = parseKey(xStr)
                            result[key] = value
                        }
                    }
                }
        }
        return result
    }
}