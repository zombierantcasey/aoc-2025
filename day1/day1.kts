import java.io.File
import java.io.InputStream
import kotlin.math.abs

fun determinePosition(currentPosition: Int, rotationSide: String, rotationMovement: Int): Int {
    var newPosition: Int? = null
    if (rotationSide == "R") {
        newPosition = currentPosition + rotationMovement
    } else {
        newPosition = currentPosition - rotationMovement
    }
    return overUnder100(newPosition)
}

fun roundDownToNearest100(number: Int): Int {
    return (number / 100) * 100
}

fun overUnder100(number: Int): Int {
    if (number > 99) {
        var nearest100 = roundDownToNearest100(number)
        return number - nearest100
    } else if (number < 0) {
        var nearest100 = roundDownToNearest100(abs(number))
        return nearest100 - abs(number)
    } else {
        return number
    }
}

fun part1() {
    val filePath = "/home/pierre/Documents/Coding/aoc-2025/day1/input.txt"
    try {
        val lines = File(filePath).readLines()
        var currentValue = 50
        var zeroCount = 0
        lines.forEachIndexed { index, line ->
            var lineArray = line.split("R").toTypedArray()
            var rotateLetter = "R"
            if (lineArray.size == 1) {
                lineArray = line.split("L").toTypedArray()
                rotateLetter = "L"
            }
            var rotationMovement = lineArray[1].toInt()
            var newValue = determinePosition(currentValue, rotateLetter, rotationMovement)
            currentValue = newValue
            if (currentValue == 0) {
                zeroCount = zeroCount + 1
            }
        }
        println(zeroCount)
    } catch (e: Exception) {
        println("Error reading file: ${e.message}")
    }
}

part1()
