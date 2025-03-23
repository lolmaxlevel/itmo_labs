package org.lolmaxlevel

import org.lolmaxlevel.logarithmic.Ln
import org.lolmaxlevel.logarithmic.LogWithBase
import org.lolmaxlevel.logarithmic.LogarithmicFunction
import org.lolmaxlevel.trigonometric.*

fun main() {
    Utils().createCsvFile("tables/sin.csv", "sin", -5.0, 11.0, 0.0001, 1e-10, Sin()::calculate)
    Utils().createCsvFile("tables/cos.csv", "cos", -5.0, 11.0, 0.0001, 1e-10, Cos()::calculate)
    Utils().createCsvFile("tables/tan.csv", "tan", -5.0, 11.0, 0.0001, 1e-10, Tan()::calculate)
    Utils().createCsvFile("tables/cot.csv", "cot", -5.0, 11.0, 0.0001, 1e-10, Cot()::calculate)
    Utils().createCsvFile("tables/sec.csv", "sec", -5.0, 11.0, 0.0001, 1e-10, Sec()::calculate)
    Utils().createCsvFile("tables/csc.csv", "csc", -5.0, 11.0, 0.0001, 1e-10, Csc()::calculate)
    Utils().createCsvFile("tables/trigFunc.csv", "trigFunc", -5.0, 11.0, 0.0001, 1e-10, TrigonometricFunction()::calculate)
    Utils().createCsvFile("tables/ln.csv", "ln", -5.0, 11.0, 0.0001, 1e-10, Ln()::calculate)
    Utils().createCsvFile("tables/ln2.csv", "ln2", -5.0, 11.0, 0.0001, 1e-10, LogWithBase(Ln(), 2.0)::calculate)
    Utils().createCsvFile("tables/ln5.csv", "ln5", -5.0, 11.0, 0.0001, 1e-10, LogWithBase(Ln(), 5.0)::calculate)
    Utils().createCsvFile("tables/ln10.csv", "ln10", -5.0, 11.0, 0.0001, 1e-10, LogWithBase(Ln(), 10.0)::calculate)
    Utils().createCsvFile("tables/logFunction.csv", "logFunction", -5.0, 11.0, 0.0001, 1e-10, LogarithmicFunction()::calculate)
    Utils().createCsvFile("tables/fullFunc.csv", "fullFunc", -5.0, 11.0, 0.0001, 1e-10, Function()::calculate)

//    Utils().createGraphFromCsv("sin.csv", "sin.png", "Sin function")
//    Utils().createGraphFromCsv("cos.csv", "cos.png", "Cos function")
//    Utils().createGraphFromCsv("tan.csv", "tan.png", "Tan function")
//    Utils().createGraphFromCsv("cot.csv", "cot.png", "Cot function")
//    Utils().createGraphFromCsv("sec.csv", "sec.png", "Sec function")
//    Utils().createGraphFromCsv("csc.csv", "csc.png", "Csc function")
//    Utils().createGraphFromCsv("trigFunc.csv", "trigFunc.png", "Trigonometric function")
//    Utils().createGraphFromCsv("ln.csv", "ln.png", "Ln function")
//    Utils().createGraphFromCsv("ln2.csv", "ln2.png", "Log with base 2 function")
//    Utils().createGraphFromCsv("ln5.csv", "ln5.png", "Log with base 5 function")
//    Utils().createGraphFromCsv("ln10.csv", "ln10.png", "Log with base 10 function")
//    Utils().createGraphFromCsv("logFunction.csv", "logFunction.png", "Logarithmic function")
//    Utils().createGraphFromCsv("fullFunc.csv", "fullFunc.png", "Full function")
}