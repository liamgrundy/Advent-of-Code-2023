import java.io.File

fun calculateDistance(holdButtonTime: Int, raceTime: Int) : Int {
    return holdButtonTime * (raceTime - holdButtonTime)
}

fun main() {
    val file = File("res/data.txt").readLines()

    val time = Regex("\\b\\d+\\b").findAll(file[0]).map { it.value.toInt() }.toList()
    val distance = Regex("\\b\\d+\\b").findAll(file[1]).map { it.value.toInt() }.toList()

    val numberOfWays = time.zip(distance).map { (t, d) ->
        List(t.toInt()) { it }.count { calculateDistance(it.toInt(), t) > d }
    }

    println("Number of ways to win each race: $numberOfWays")
    println("Product: ${numberOfWays.fold(1) { t, it -> t * it }}")
}
