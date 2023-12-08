import java.io.File

fun getNextNode(currentNode: String, instruction: Char, nodes: List<List<String>>) : String {
    val foundNode = nodes.find { it[0] == currentNode } ?:return currentNode
    return when (instruction) {
        'L' -> foundNode[1]
        'R' -> foundNode[2]
        else -> currentNode
    }
}

fun main(args: Array<String>) {
    val file = File("res/data.txt").readLines()

    val instructions = file[0]
    val nodes = file.drop(2).map { Regex("\\b\\w+\\b").findAll(it).map { x -> x.value }.toList() }

    var currentNode = "AAA"
    var steps = 0
    while (currentNode != "ZZZ") {
        for (instruction in instructions) {
            currentNode = getNextNode(currentNode, instruction, nodes)
            steps++
            if (currentNode == "ZZZ")
                break
        }
    }

    println("End reached in $steps steps")
}
