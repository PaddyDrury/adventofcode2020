package day23

typealias Circle = IntArray

class Day23(val input: String) {
    val startingCups = input.map(Char::toString).map(String::toInt)

    fun part1(): String = makeMoves(
        100,
        startingCups.first(),
        startingCups.toCircle(),
        startingCups.minOrNull()!!,
        startingCups.maxOrNull()!!
    ).let { cups ->
        cupsToTheRightOf(1, cups, startingCups.size - 1)
    }.joinToString("")

    fun part2(): Long {
        val cups = IntArray(1000000) { it + 2}
        startingCups.toCircle().forEachIndexed { idx, it ->
            cups[idx] = it
        }
        cups.setNextCup(startingCups.last(), 10)
        cups.setNextCup(1000000, startingCups.first())
        makeMoves(10000000,
            startingCups.first(),
            cups,
            1,
            1000000
        )
        return cupsToTheRightOf(1, cups, 2).fold(1L) { acc, i -> acc * i }
    }

    fun makeMoves(numMoves: Int, firstCup: Int, circle: Circle, min: Int, max: Int): Circle {
        var currentCup = firstCup
        repeat(numMoves) {
            currentCup = makeMove(circle, currentCup, min, max)
        }
        return circle
    }

    fun makeMove(circle: Circle, currentCup: Int, min: Int, max: Int): Int {
        val removedCups = cupsToTheRightOf(currentCup, circle, 3)
        val destinationCup = destinationCup(currentCup, removedCups, min, max)
        circle.setNextCup(currentCup, circle.getNextCup(removedCups.last()))
        circle.insertAfter(destinationCup, removedCups)
        return circle.getNextCup(currentCup)
    }

    fun cupsToTheRightOf(currentCup: Int, circle: Circle, numCups: Int): List<Int> = generateSequence(circle.getNextCup(currentCup)) { circle.getNextCup(it) }.take(numCups).toList()

    fun destinationCup(currentCup: Int, pickedCups: List<Int>, min: Int, max: Int): Int {
        var destCup = currentCup
        do {
            if (--destCup < min) destCup = max
        } while (destCup in pickedCups)
        return destCup
    }
}

fun Circle.insertAfter(cup: Int, cups: List<Int>) = this.getNextCup(cup).let { next ->
    this.setNextCup(cup, cups.first())
    this.setNextCup(cups.last(), next)
}
fun Circle.getNextCup(cup: Int): Int = this[cup - 1]
fun Circle.setNextCup(cup: Int, nextCup: Int) = let {this[cup - 1] = nextCup }

fun List<Int>.toCircle(): Circle = this.mapIndexed { idx, it ->
    val nextIndex = idx + 1
    it to this[if (nextIndex < this.size) nextIndex else 0]
}.let {
    val arr = IntArray(it.size)
    it.forEach { p -> arr[p.first - 1] = p.second}
    arr
}