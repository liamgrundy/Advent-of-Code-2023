import java.io.File

fun calculateDistance(holdButtonTime: Long, raceTime: Long) : Long {
    return holdButtonTime * (raceTime - holdButtonTime)
}

fun main() {
    val file = File("res/data.txt").readLines()

    val time = Regex("\\b\\d+\\b").findAll(file[0].replace(" ", "")).map { it.value.toLong() }.toList()
    val distance = Regex("\\b\\d+\\b").findAll(file[1].replace(" ", "")).map { it.value.toLong() }.toList()

    val numberOfWays = time.zip(distance).map { (t, d) ->
        List(t.toInt()) { it }.count { calculateDistance(it.toLong(), t) > d }
    }

    println("Number of ways to win race: ${numberOfWays.first()}")
}
