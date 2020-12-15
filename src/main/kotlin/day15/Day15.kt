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