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

fun main() {
    val file = File("res/data.txt").readText()

    // Assume each block of data is separated by an empty line. convert to sequence of List<String>
    val filedata = Regex("(?s).*?(?=\\n\\n|\$)").findAll(file).map { it.value.trim().split('\n') }

    val seeds = parseNumbers(getMapData("seeds", filedata).first().drop("seeds: ".length))

    // Maps
    val seedToSoil = getMap("seed-to-soil", filedata)
    val soilToFertilizer = getMap("soil-to-fertilizer", filedata)
    val fertilizerToWater = getMap("fertilizer-to-water", filedata)
    val waterToLight = getMap("water-to-light", filedata)
    val lightToTemperature = getMap("light-to-temperature", filedata)
    val temperatureToHumidity = getMap("temperature-to-humidity", filedata)
    val humidityToLocation = getMap("humidity-to-location", filedata)

    val soil = seeds.map { mapValue(it, seedToSoil) }
    val fertilizer = soil.map { mapValue(it, soilToFertilizer) }
    val water = fertilizer.map { mapValue(it, fertilizerToWater) }
    val light = water.map { mapValue(it, waterToLight) }
    val temperature = light.map { mapValue(it, lightToTemperature) }
    val humidity = temperature.map { mapValue(it, temperatureToHumidity) }
    val location = humidity.map { mapValue(it, humidityToLocation) }

    println("Part 1:")
    println("Min location value: ${location.min()}")
}
