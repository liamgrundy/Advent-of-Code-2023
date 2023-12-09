import java.io.File

fun getDerivative(input: List<Int>) : List<Int> {
    return input.drop(1).zip(input.dropLast(1)).map { (x, y) ->
        x - y
    }
}

fun getNextValue(input: List<Int>) : Int {
    val placeholder: MutableList<Int> = MutableList(0) { 0 }
    var list = input
    do {
        placeholder.addLast(list.last())
        list = getDerivative(list)
    }
    while (!list.all { it == 0 })
    return placeholder.sum()
}

fun getPrevValue(input: List<Int>) : Int {
    val placeholder: MutableList<Int> = MutableList(0) { 0 }
    var list = input
    do {
        placeholder.addLast(list.first())
        list = getDerivative(list.map { -it })
    }
    while (!list.all { it == 0 })
    return placeholder.sum()
}

fun main(args: Array<String>) {
    val file = File("res/data.txt").readLines()
    val lines = file.map { it.split("\\s++".toRegex()).map { x -> x.toInt() } }

    val nextValueList = lines.map { getNextValue(it) }
    val prevValueList = lines.map { getPrevValue(it) }
    println("Sum of next values: ${nextValueList.sum()}")
    println("Sum of prev values: ${prevValueList.sum()}")
}
