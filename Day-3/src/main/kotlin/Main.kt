import java.io.File
import kotlin.math.*

var partNumberSum = 0

fun printPartNumbers(prevLine: String?, line: String, nextLine: String?) {
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
    printPartNumbers(null, file[0], file[1])

    for ((prevLine, line, nextLine) in file.windowed(3)) {
        printPartNumbers(prevLine, line, nextLine)
    }

    // Edge case: no next line
    printPartNumbers(file[file.size - 2], file[file.size - 1], null)

    println("""--------------------
        |Sum of part numbers: $partNumberSum""".trimMargin())
}
