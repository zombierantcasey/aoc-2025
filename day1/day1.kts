import java.io.File
import java.io.InputStream
import kotlin.math.abs

var currentValue = 50
var zeroCount = 0
var zeroRevolutionCount = 0
var rotateLetter = "R"
var rotationMovement = 0

fun determinePosition(currentPosition: Int, rotationSide: String, rotationMovement: Int): Int {
    if (rotationSide == "R") {
        return (currentPosition + rotationMovement) % 100
    } else {
        val result = (currentPosition - rotationMovement) % 100
        return if (result < 0) result + 100 else result
    }
}

fun roundDownToNearest100(number: Int): Int {
    return (number / 100) * 100
}

fun detemineRevolution() {
    val newPosition = determinePosition(currentValue, rotateLetter, rotationMovement)
    val firstHit = if (rotateLetter == "R") {
        if (currentValue == 0) 100 else 100 - currentValue
    } else {
        if (currentValue == 0) 100 else currentValue
    }
    var revolutions = 0
    if (rotationMovement >= firstHit) {
        revolutions = 1 + (rotationMovement - firstHit) / 100
    }
    if (newPosition == 0 && revolutions > 0) {
        revolutions -= 1
    }
    zeroRevolutionCount += revolutions
}

fun overUnder100(number: Int): Int {
    var determinedPosition = 0
    if (number > 99) {
        var nearest100 = roundDownToNearest100(number)
        determinedPosition = number - nearest100
        return determinedPosition
    } else if (number < 0) {
        var nearest100 = roundDownToNearest100(abs(number))
        if (nearest100 == 0) {
            nearest100 = 100
        }
        return nearest100 - abs(number)
    } else {
        return number
    }
}

fun part1_and_2() {
    val filePath = "/home/pierre/Documents/Coding/aoc-2025/day1/input.txt"
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
            detemineRevolution()
            val newValue = determinePosition(currentValue, rotateLetter, rotationMovement)
            currentValue = newValue
            if (currentValue == 0) {
                zeroCount = zeroCount + 1
            }
        }
        println("Part 1: " + zeroCount)
        val part2 = zeroCount + zeroRevolutionCount
        println("Part 2: " + part2)
    } catch (e: Exception) {
        println("Error reading file: ${e.message}")
    }
}

part1_and_2()
