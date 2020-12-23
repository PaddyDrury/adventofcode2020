package day07

import util.readFile

class Day7(inputFile: String) {
    private val lines: List<String> = readFile(inputFile)
    private val lineRegex = """^([a-z]+ [a-z]+) bags contain (.*)${'$'}""".toRegex()
    private val bagRegex = """(\d)+\s([a-z]+\s[a-z]+)""".toRegex()

    class Bag(val colour: String, private val children: MutableMap<Bag, Int>) {
        fun containsBagOfColour(colour: String): Boolean = children.keys.map { it.colour }.contains(colour) || children.keys.any { it.containsBagOfColour(colour) }

        fun countChildBags(): Long = children.entries.fold(0L) { acc, e ->
            acc + ((e.key.countChildBags() + 1) * e.value)
        }

        fun populateChildren(bags: Map<String, Bag>) {
            if (this.children.isEmpty() && bags[colour]!!.children.isNotEmpty()) {
                this.children.putAll(bags[colour]!!.children)
            }
            this.children.keys.forEach { it.populateChildren(bags) }
        }
    }

    fun part1(): Int = countBagsWhichCanContainBagOfColour("shiny gold")
    fun part2(): Long = countBagsWithinBagOfColour("shiny gold")

    fun countBagsWhichCanContainBagOfColour(colour: String): Int = parseBagMappings().count {
        it.value.containsBagOfColour(colour)
    }

    fun countBagsWithinBagOfColour(colour: String): Long = parseBagMappings()[colour]?.countChildBags() ?: 0

    fun parseBagMappings(): Map<String, Bag> {
        val bags = lines.map { parseBagMapping(it) }.associateBy { it.colour }
        bags.values.forEach { it.populateChildren(bags) }
        return bags
    }

    fun parseBagMapping(line: String): Bag {
        val (colour, bagListString) = lineRegex.find(line)!!.destructured
        return Bag(colour, parseBagList(bagListString))
    }

    private fun parseBagList(bagListString: String): MutableMap<Bag, Int> = bagRegex.findAll(bagListString).associate {
        val (count, colour) = it.destructured
        Pair(Bag(colour, mutableMapOf()), count.toInt())
    }.toMutableMap()

}