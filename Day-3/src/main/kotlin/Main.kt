import java.io.File
import kotlin.math.*

var partNumberSum = 0
var gearRatioSum: Long = 0

fun accumulateGearRatios(prevLine: String, line: String, nextLine: String) {
    val symbolsList = Regex("\\*").findAll(line)
    val numberInPrev = Regex("\\b\\d+\\b").findAll(prevLine)
    val numberInLine = Regex("\\b\\d+\\b").findAll(line)
    val numberInNext = Regex("\\b\\d+\\b").findAll(nextLine)

    symbolsList.forEach { symbol ->
        val gearPartNumbers = numberInPrev.filter { number ->
            number.range.first - 1 <= symbol.range.first && symbol.range.last <= number.range.last + 1
        } + numberInNext.filter { number ->
            number.range.first - 1 <= symbol.range.first && symbol.range.last <= number.range.last + 1
        } + numberInLine.filter { number ->
            number.range.first == symbol.range.last + 1 || number.range.last == symbol.range.first - 1
        }

        gearPartNumbers.forEach { println("val: ${it.value}") }

        if (gearPartNumbers.count() == 2) {
            val num1 = gearPartNumbers.first().value.toLong()
            val num2 = gearPartNumbers.last().value.toLong()
            gearRatioSum += num1 * num2
        }
    }
}

fun accumulatePartNumbers(prevLine: String?, line: String, nextLine: String?) {
    val numbersList = Regex("\\b\\d+\\b").findAll(line)

    numbersList.forEach { number ->
        var isPartNumber = false

        // Contains symbol before number
        if (number.range.first > 0 && line[number.range.first - 1] != '.')
            isPartNumber = true

        // Contains symbol after number
        if (number.range.last < line.length - 1 && line[number.range.last + 1] != '.')
            isPartNumber = true

        // Contains symbol above number
        if (!prevLine.isNullOrEmpty() && prevLine.substring(max(0, number.range.first - 1),
                min(prevLine.length - 1, number.range.last + 2)).any { c -> !c.isDigit() && c != '.' })
            isPartNumber = true

        // Contains symbol below number
        if (!nextLine.isNullOrEmpty() && nextLine.substring(max(0, number.range.first - 1),
                min(nextLine.length - 1, number.range.last + 2)).any { c -> !c.isDigit() && c != '.' })
            isPartNumber = true

        if (isPartNumber) {
            println(number.value)
            partNumberSum += number.value.toInt()
        }
    }
}

fun main() {
    val file = File("res/data.txt").readLines()

    println("""List of part numbers:
        |--------------------""".trimMargin())

    // Edge case: no previous line
    accumulatePartNumbers(null, file[0], file[1])

    for ((prevLine, line, nextLine) in file.windowed(3)) {
        accumulatePartNumbers(prevLine, line, nextLine)
        accumulateGearRatios(prevLine, line, nextLine)
    }

    // Edge case: no next line
    accumulatePartNumbers(file[file.size - 2], file[file.size - 1], null)

    println("""--------------------
        |Sum of part numbers: $partNumberSum""".trimMargin())
    println("Sum of gear ratios: $gearRatioSum")
}
