package day23

typealias Cups = MutableMap<Int, Int>

class Day23(val input: String) {
    val startingCups = input.map(Char::toString).map(String::toInt)

    fun part1(): String = makeMoves(
        100,
        startingCups.first(),
        startingCups.toCircularMap().toMutableMap(),
        startingCups.minOrNull()!!,
        startingCups.maxOrNull()!!
    ).let { cups ->
        cupsToTheRightOf(1, cups, startingCups.size - 1)
    }.joinToString("")

    fun part2(): Long {
        val cups = startingCups.toCircularMap().toMutableMap()
        cups.putAll((10..1000000).map { it to it+1 }.toMap())
        cups[startingCups.last()] = 10
        cups[1000000] = startingCups.first()
        makeMoves(10000000,
            startingCups.first(),
            cups,
            1,
            1000000
        )
        return cupsToTheRightOf(1, cups, 2).fold(1L) { acc, i -> acc * i }
    }

    fun makeMoves(numMoves: Int, firstCup: Int, cups: Cups, min: Int, max: Int): Cups {
        var currentCup = firstCup
        repeat(numMoves) {
            currentCup = makeMove(cups, currentCup, min, max)
        }
        return cups
    }

    fun makeMove(cups: Cups, currentCup: Int, min: Int, max: Int): Int {
        val removedCups = cupsToTheRightOf(currentCup, cups, 3)
        val destinationCup = destinationCup(currentCup, removedCups, min, max)
        cups[currentCup] = cups[removedCups.last()]!!
        insertAfter(destinationCup, removedCups, cups)
        return cups[currentCup]!!
    }

    fun insertAfter(cup: Int, cupsToInsert: List<Int>, cups: Cups) {
        val nextCup = cups[cup]!!
        cups[cup] = cupsToInsert.first()
        cups[cupsToInsert.last()] = nextCup
    }

    fun cupsToTheRightOf(currentCup: Int, cups: Cups, numCups: Int): List<Int> = generateSequence(cups[currentCup]) { cups[it]!! }.take(numCups).toList()

    fun destinationCup(currentCup: Int, pickedCups: List<Int>, min: Int, max: Int): Int {
        var destCup = currentCup
        do {
            if (--destCup < min) destCup = max
        } while (destCup in pickedCups)
        return destCup
    }
}

fun <T> List<T>.toCircularMap(): Map<T, T> = this.mapIndexed { idx, it ->
    val nextIndex = idx + 1
    it to this[if (nextIndex < this.size) nextIndex else 0]!!
}.toMap()