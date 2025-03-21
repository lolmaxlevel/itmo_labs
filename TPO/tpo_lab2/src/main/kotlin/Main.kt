package org.lolmaxlevel

import org.lolmaxlevel.logarithmic.Ln

fun main() {
    Utils().createCsvFile("ln.csv", "ln", -10.0, 15.0, 0.001, 1e-10, Ln()::calculate)
}