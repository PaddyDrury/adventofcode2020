package day15

data class Day15(val numbers: MutableList<Int>) {
    fun part1(): Int {
        while(numbers.size < 2020) {
            numbers.add(nextNumber())
        }
        println(numbers)
        return numbers.last()
    }

    fun nextNumber(): Int = numbers.dropLast(1).reversed().indexOf(numbers.last()) + 1
}

data class Day15Map(val startingNumbers: List<Int>) {
    var count = startingNumbers.size
    var nextVal = 0
    val map = startingNumbers.foldIndexed(mutableMapOf<Int,Int>()) { index, acc, i ->
        nextVal = acc.nextVal(i, index)
        acc
    }

    fun part1(): Int {
        while(count < 2020 - 1) {
            nextVal = map.nextVal(nextVal, count++)
        }
        return nextVal
    }

    fun part2(): Int {
        while(count < 30000000 - 1) {
            nextVal = map.nextVal(nextVal, count++)
        }
        return nextVal
    }
}

fun MutableMap<Int, Int>.nextVal(value: Int, turn: Int): Int {
    val nextVal = turn - (this[value] ?: turn)
    this[value] = turn
    return nextVal
}