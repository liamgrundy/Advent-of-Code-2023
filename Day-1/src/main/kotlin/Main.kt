import java.io.File

val numbers = arrayOf(
    Pair("one", "1"),
    Pair("two", "2"),
    Pair("three", "3"),
    Pair("four", "4"),
    Pair("five", "5"),
    Pair("six", "6"),
    Pair("seven", "7"),
    Pair("eight", "8"),
    Pair("nine", "9")
)

fun main() {
    val file = File("res/data.txt").readLines()

    val calibrationValueSum = file.fold(0) { calibrationValue, line ->
        // Allows pattern matching of overlapping numbers
        val digits = numbers.fold(Regex("\\d").findAll(line)) { s, value ->
            (s + Regex(value.first).findAll(line))
        }.sortedBy { it.range.first }.map { map ->
            numbers.find { it.first == map.value }?.second ?:map.value
        }

        calibrationValue + if (digits.count() != 0) {
            println("$line: ${digits.toList().fold("") { s, match ->
                s + if (s.isNotEmpty()) {", "} else {""} + match
            }}")
            (digits.first() + digits.last()).toInt() // Accumulated to calibrationValue
        } else {
            println("$line: does not contain number")
            0
        }
    }

    println("Sum: $calibrationValueSum")
}
