import java.io.File

fun main() {
    val file = File("res/data.txt").readLines()

    val cardScores = file.map { line ->
        val values = line.substringAfter(':')
        val winning = Regex("\\b\\d+\\b").findAll(values.substringBefore('|')).map {
            it.value
        }
        val numbers = Regex("\\b\\d+\\b").findAll(values.substringAfter('|')).map {
            it.value
        }

        numbers.filter { it in winning }.count()
    }

    val cardsSeen = cardScores.foldIndexed(List(cardScores.size) { 1 }) { i, tot, it ->
        tot.zip(List(cardScores.size) { index ->
            if (index > i && index - i <= it) { tot[i] } else { 0 }
        }).map {
            it.first + it.second
        }
    }

    // Print data for each scratch card
    cardScores.zip(cardsSeen).forEachIndexed { index, pair ->
        println("Card ${index + 1}: number of winning numbers: ${pair.first}; number of cards seen: ${pair.second}")
    }

    println("Number of cards seen: ${cardsSeen.fold(0) { x, y -> x + y }}")
}
