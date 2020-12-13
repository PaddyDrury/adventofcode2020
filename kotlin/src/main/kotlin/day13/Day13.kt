package day13

import util.readFile

class Day13(inputFile: String) {
    private val lines: List<String> = readFile(inputFile)
    private val time = lines.first().toInt()
    private val knownBusIds = lines.last().split(',').filter { it != "x" }.map(String::toInt)
    private val busIdsAndDepartureTimes = lines.last()
            .split(',')
            .withIndex()
            .filter { it.value != "x" }
            .map { Pair(it.value.toInt(), it.index) }
    private val busIdsAndDepartureTimesMap = busIdsAndDepartureTimes
            .toMap()
            .toSortedMap(compareByDescending { it })
    private val maxBusId = knownBusIds.maxOrNull()!!
    private val maxBusIdDepartureOffset = busIdsAndDepartureTimesMap[maxBusId] ?: error("")

    fun part2SlowAF(): Long = generateSequence(0L, {it + maxBusId})
            .first { maxBusIdTime ->
                 busIdsAndDepartureTimesMap.all {
                     it.key == maxBusId ||
                             ((maxBusIdTime + it.value - maxBusIdDepartureOffset) % it.key) == 0L
                 }
            }.minus(maxBusIdDepartureOffset)

    fun part2(): Long = busIdsAndDepartureTimes.fold(Pair(0L, 1L)) { acc, busIdOffset ->
        var target = acc.first
        var step = acc.second
        val busId = busIdOffset.first
        val offsetFromTarget = busIdOffset.second
        var stepCount = 0
        while((target + offsetFromTarget) % busId != 0L) {
            target += step
            stepCount++
        }
        println("target=$target,step=$step,busID=$busId,offset=$offsetFromTarget,stepCount=$stepCount")
        step *= busId
        Pair(target, step)
    }.first


    fun part1(): Int = knownBusIds.associateWith { busId ->
        busId - (time % busId)
    }.minByOrNull { it.value }!!.toPair().let {
        it.first * it.second
    }


}