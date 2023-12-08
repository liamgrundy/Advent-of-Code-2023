import java.io.File

fun rulePart1(hands: List<Pair<String, ULong>>) : ULong {
    val handType = hands.map {
        it.first.toSet().map { char -> it.first.count { handChar -> handChar == char } }.sortedDescending()
    }

    return hands.zip(handType).sortedBy { (hand, type) ->
        val string = hand.first.replace(Regex("\\w")) { match ->
            // Replace characters so that string boolean comparison works
            when (match.value) {
                "T" -> "A"
                "J" -> "B"
                "Q" -> "C"
                "K" -> "D"
                "A" -> "E"
                else -> match.value
            }
        }
        type.toString().padEnd(5, '0') + string
    }.foldIndexed(0UL) { i, tot, it -> tot + it.first.second * (i + 1).toULong() }
}

fun rulePart2(hands: List<Pair<String, ULong>>) : ULong {
    val handType = hands.map {
        val jokerCount = it.first.count { c -> c == 'J'}
        val list = it.first.toSet().map { char -> it.first.count {
            handChar -> handChar == char && handChar != 'J'
        } }.sortedDescending().toMutableList()
        list[0] += jokerCount
        if (list.size > 1 && jokerCount > 0) list.dropLast(1) else list
    }

    return hands.zip(handType).sortedBy { (hand, type) ->
        val string = hand.first.replace(Regex("\\w")) { match ->
            // Replace characters so that string boolean comparison works
            when (match.value) {
                "T" -> "A"
                "J" -> "0"
                "Q" -> "C"
                "K" -> "D"
                "A" -> "E"
                else -> match.value
            }
        }
        type.toString().padEnd(5, '0') + string
    }.foldIndexed(0UL) { i, tot, it -> tot + it.first.second * (i + 1).toULong() }
}

fun main() {
    val file = File("res/data.txt").readLines()

    val hands = file.map {
        val (hand, bet) = it.split(" ")
        Pair(hand, bet.toULong())
    }

    println("Total winnings in part 1: ${rulePart1(hands)}")
    println("Total winnings in part 2: ${rulePart2(hands)}")
}
