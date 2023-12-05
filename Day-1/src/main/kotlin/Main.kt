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

    var calibrationValueSum = 0

    for (line in file) {
        // Allows pattern matching of overlapping numbers
        val digits = numbers.fold(Regex("\\d").findAll(line)) { s, value ->
            (s + Regex(value.first).findAll(line))
        }.sortedBy { it.range.first }.map { map ->
            numbers.find { it.first == map.value }?.second ?:map.value
        }
        if (digits.count() != 0) {
            calibrationValueSum += (digits.first() + digits.last()).toInt()
            println("$line: ${digits.toList().fold("") { s, match -> 
                s + if (s.isNotEmpty()) {", "} else {""} + match
            }}")
        }
        else {
            println("$line: does not contain number")
        }
    }

    println("Sum: $calibrationValueSum")
}
