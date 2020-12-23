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
    ).let { circle ->
        circle.getNextCups(1, startingCups.size - 1)
    }.joinToString("")

    fun part2(): Long {
        val circle = IntArray(1000000) { it + 2}
        startingCups.toCircle().forEachIndexed { idx, it ->
            circle[idx] = it
        }
        circle.setNextCup(startingCups.last(), 10)
        circle.setNextCup(1000000, startingCups.first())
        makeMoves(10000000,
            startingCups.first(),
            circle,
            1,
            1000000
        )
        return circle.getNextCups(1, 2).fold(1L) { acc, i -> acc * i }
    }

    fun makeMoves(numMoves: Int, firstCup: Int, circle: Circle, min: Int, max: Int): Circle {
        var currentCup = firstCup
        repeat(numMoves) {
            currentCup = makeMove(circle, currentCup, min, max)
        }
        return circle
    }

    fun makeMove(circle: Circle, currentCup: Int, min: Int, max: Int): Int {
        val removedCups = circle.getNextCups(currentCup, 3)
        val destinationCup = destinationCup(currentCup, removedCups, min, max)
        circle.setNextCup(currentCup, circle.getNextCup(removedCups.last()))
        circle.insertAfter(destinationCup, removedCups)
        return circle.getNextCup(currentCup)
    }

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
fun Circle.getNextCups(cup: Int, numCups: Int): List<Int> = generateSequence(this.getNextCup(cup)) { this.getNextCup(it) }.take(numCups).toList()

fun List<Int>.toCircle(): Circle = this.mapIndexed { idx, it ->
    val nextIndex = idx + 1
    it to this[if (nextIndex < this.size) nextIndex else 0]
}.let {
    val arr = IntArray(it.size)
    it.forEach { p -> arr[p.first - 1] = p.second}
    arr
}