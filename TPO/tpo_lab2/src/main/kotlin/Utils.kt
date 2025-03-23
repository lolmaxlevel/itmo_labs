package org.lolmaxlevel

import java.io.InputStreamReader

class Utils {
    fun createCsvFile(
        fileName: String, functionName: String, start: Double, end: Double,
        step: Double, epsilon: Double, function: (Double, Double) -> Double
    ) {
        val file = java.io.File(fileName)
        file.parentFile?.mkdirs() // Create parent directories if they don't exist
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

    fun createGraphFromCsv(
        csvFileName: String,
        outputImagePath: String,
        title: String = "Function Graph",
        xAxisLabel: String = "X",
        yAxisLabel: String = "Y",
        width: Int = 800,
        height: Int = 600,
        yMin: Double = -50.0,
        yMax: Double = 50.0
    ) {
        // Load data from CSV
        val dataMap = loadFromCsv(csvFileName)

        // Prepare data for plotting
        val xData = mutableListOf<Double>()
        val yData = mutableListOf<Double>()

        dataMap.forEach { (x, y) ->
            if (y in yMin..yMax) {
                xData.add(x)
                yData.add(y)
            }
        }

        val chart = org.knowm.xchart.XYChartBuilder()
            .width(width)
            .height(height)
            .title(title)
            .xAxisTitle(xAxisLabel)
            .yAxisTitle(yAxisLabel)
            .build()

        // Add data series to chart
        val series = chart.addSeries("f(x)", xData, yData)

        series.lineStyle = org.knowm.xchart.style.lines.SeriesLines.NONE

        // Save chart to file
        org.knowm.xchart.BitmapEncoder.saveBitmap(
            chart,
            outputImagePath,
            org.knowm.xchart.BitmapEncoder.BitmapFormat.PNG
        )

        println("Graph saved: $outputImagePath")
    }
}