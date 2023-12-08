import java.io.File

fun parseNumbers(data: String) : List<Long> {
    return Regex("\\b\\d+\\b").findAll(data).map { it.value.toLong() }.toList()
}

// returns the block of data which the heading containing mapName, otherwise empty list
fun getMapData(mapName: String, filedata: Sequence<List<String>>) : List<String> {
    return filedata.find { it.first().contains(mapName) }?: List(0) { "" }
}

// Same as getMapData() but removes block heading and parses the values to long
fun getMap(mapName: String, filedata: Sequence<List<String>>) : List<List<Long>> {
    return getMapData(mapName, filedata).drop(1).map { parseNumbers(it) }
}

fun mapValue(value: Long, map: List<List<Long>>) : Long {
    val mapValues = map.find { value >= it[1] && value < it[1] + it[2] } ?: return value
    return value + mapValues[0] - mapValues[1]
}

// Inputs seed and the set of maps and returns the location
fun getLocation(seed: Long, maps: List<List<List<Long>>>) : Long {
    return maps.fold(seed) { result, map -> mapValue(result, map) }
}

fun main() {
    val file = File("res/data.txt").readText()

    // Assume each block of data is separated by an empty line. convert to sequence of List<String>
    val filedata = Regex("(?s).*?(?=\\n\\n|\$)").findAll(file).map { it.value.trim().split('\n') }

    val seeds = parseNumbers(getMapData("seeds", filedata).first().drop("seeds: ".length))

    // Maps
    val mapNames = listOf(
        "seed-to-soil",
        "soil-to-fertilizer",
        "fertilizer-to-water",
        "water-to-light",
        "light-to-temperature",
        "temperature-to-humidity",
        "humidity-to-location"
    )
    val maps = mapNames.map { getMap(it, filedata) }
    val location = seeds.map { getLocation(it, maps) }

    println("Part 1:")
    println("Min location value: ${location.min()}")

    val seedPairs = List(seeds.size / 2) {
        val start = seeds[2 * it]
        val length = seeds[2 * it + 1]
        mutableListOf(start, length)
    }.sortedBy { it[0] }

    // Cannot reduce ranges by removing repeated calculations because no overlap

    println("Part 2:")

    // Iterative algorithm: need different algorithm
    val minLocationSeed = seedPairs.map {
        var min: Long = getLocation(it[0], maps)
        for (i in it[0].. (it[0] + it[1])) {
            val loc = getLocation(i, maps)
            if (loc < min) {
                min = loc
            }
        }
        println("range: ${it}, min: $min")
        min
    }

    println("Min location for seed ranges: ${minLocationSeed.min()}")
}
