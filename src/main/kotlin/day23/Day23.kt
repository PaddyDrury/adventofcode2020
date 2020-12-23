package day23

typealias Cups = List<Int>

class Day23(val input: String) {
    val startingCups = input.map(Char::toString).map(String::toInt)

    fun part1(): String = cupsAfter(1, makeMoves(100, startingCups))

    fun part2(): Long = makeMoves(10000000,
        startingCups + ((startingCups.maxOrNull()!! + 1)..1000000).toList()).let {
        val indexOf1 = it.indexOf(1)
        it[indexOf1 + 1].toLong() * it[indexOf1 + 2].toLong()
    }

    fun cupsAfter(cup: Int, cups: Cups): String {
        return (cups.dropWhile { it != cup }.drop(1) + cups.takeWhile { it != cup }).joinToString("")
    }

    fun makeMoves(numMoves: Int, startingCups: Cups): Cups {
        var cups = startingCups
        repeat(numMoves) {
            if(it % 10 == 0) {
                println("made $it moves")
            }
            cups = makeMove(cups)
        }
        return cups
    }

    fun makeMove(cups: Cups): Cups {
        val currentCup = cups.take(1).first()
        val pickedUpCups = cups.take(4).drop(1)
        val remainingCups = cups.drop(4).toMutableList()
        val destinationCup = findDestinationCup(currentCup, remainingCups)
        remainingCups.addAll(remainingCups.indexOf(destinationCup) + 1, pickedUpCups)
        remainingCups.add(currentCup)
        return remainingCups
    }

    fun findDestinationCup(currentCup: Int, remainingCups: Cups): Int {
        var destinationCup = currentCup
        do {
            destinationCup--
        } while (!remainingCups.contains(destinationCup) && remainingCups.any { it > destinationCup })
        return if(remainingCups.contains(destinationCup)) destinationCup else remainingCups.maxOrNull()!!
    }

}