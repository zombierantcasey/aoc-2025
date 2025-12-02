import java.io.File
import java.io.InputStream
import kotlin.math.abs

var currentValue = 50
var zeroCount = 0
var zeroRevolutionCount = 0
var rotateLetter = "R"
var rotationMovement = 0

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

fun detemineRevolution(nearest100: Int) {
    var movement = 0
    if (rotateLetter == "L") {
        movement = currentValue - rotationMovement
        if (movement < 0) {
            if (currentValue != 0 && 100 - abs(movement) < 100) {
                zeroRevolutionCount += 1
            }
        }
    } else if (rotateLetter == "R") {
        movement = currentValue + rotationMovement
        if (movement > 100) {
            zeroRevolutionCount += 1
        }
    }
}

fun overUnder100(number: Int): Int {
    var determinedPosition = 0
    if (number > 99) {
        var nearest100 = roundDownToNearest100(number)
        determinedPosition = number - nearest100
        detemineRevolution(nearest100)
        return determinedPosition
    } else if (number < 0) {
        var nearest100 = roundDownToNearest100(abs(number))
        if (nearest100 == 0) {
            nearest100 = 100
        }
        detemineRevolution(nearest100)
        return nearest100 - abs(number)
    } else {
        return number
    }
}

fun part1() {
    val filePath = "/home/pierre/Documents/Coding/aoc-2025/day1/test_input.txt"
    try {
        val lines = File(filePath).readLines()
        lines.forEachIndexed { index, line ->
            var lineArray = line.split("R").toTypedArray()
            rotateLetter = "R"
            if (lineArray.size == 1) {
                lineArray = line.split("L").toTypedArray()
                rotateLetter = "L"
            }
            rotationMovement = lineArray[1].toInt()
            var newValue = determinePosition(currentValue, rotateLetter, rotationMovement)
            currentValue = newValue
            if (currentValue == 0) {
                zeroCount = zeroCount + 1
            }
        }
        println("Part 1: " + zeroCount)
        var part2 = zeroCount + zeroRevolutionCount
        println("Part 2: " + part2)
    } catch (e: Exception) {
        println("Error reading file: ${e.message}")
    }
}

part1()
