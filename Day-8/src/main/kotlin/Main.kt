import java.io.File
import kotlin.math.min

fun getNextNode(currentNode: String, instruction: Char, nodes: List<List<String>>) : String {
    val foundNode = nodes.find { it[0] == currentNode } ?:return currentNode
    return when (instruction) {
        'L' -> foundNode[1]
        'R' -> foundNode[2]
        else -> currentNode
    }
}

fun getEndNode(startNode: String, instructions: String, nodes: List<List<String>>) : Pair<String, ULong> {
    var currentNode = startNode
    var steps = 0UL
    while (!currentNode.endsWith('Z')) {
        for (instruction in instructions) {
            currentNode = getNextNode(currentNode, instruction, nodes)
            steps++
            if (currentNode.endsWith('Z'))
                break
        }
    }

    return Pair(currentNode, steps)
}

fun gcd(x: ULong, y: ULong) : ULong {
    var gcd = 1UL
    for (i in 2UL..min(x, y)) {
        if ((x % i == 0UL) and (y % i == 0UL))
            gcd = i
    }
   return gcd
}

fun lcm(x: ULong, y: ULong) : ULong {
    return x * y / gcd(x, y)
}

fun main() {
    val file = File("res/data.txt").readLines()

    val instructions = file[0]
    val nodes = file.drop(2).map { Regex("\\b\\w+\\b").findAll(it).map { x -> x.value }.toList() }

    val currentNodes = nodes.filter { it[0].last() == 'A' }.map { it[0] }
    println("Start nodes: $currentNodes")
    val stepsToEnd = currentNodes.map { getEndNode(it, instructions, nodes).second }
    val steps = stepsToEnd.fold(stepsToEnd.first()) { lcm, x ->
        lcm(x, lcm)
    }

    println("End reached in $steps steps")
}
