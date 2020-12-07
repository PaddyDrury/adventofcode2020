package day7

import util.readFile

class Day7(inputFile: String) {
    private val lines: List<String> = readFile(inputFile)
    private val lineRegex = """^([a-z]+ [a-z]+) bags contain (.*)${'$'}""".toRegex()
    private val bagRegex = """(\d)+\s([a-z]+\s[a-z]+)""".toRegex()

    class Bag(val colour: String, val bags: MutableMap<Bag, Int>) {
        fun containsBagOfColour(colour: String): Boolean = directlyContainsBagOfColour(colour) || bags.keys.any { it.containsBagOfColour(colour)}
        fun directlyContainsBagOfColour(colour: String) =  bags.keys.map { it.colour }.contains(colour)

        fun countForColour(colour: String): Int = bags.filterKeys { it.colour == colour }.values.sum()


        fun putIfPresent(bag: Bag) {
            if(this != bag) {
                if (directlyContainsBagOfColour(bag.colour)) {
                    bags.put(bag, countForColour(bag.colour))
                } else {
                    bags.keys.filter { containsBagOfColour(bag.colour) }.forEach(::putIfPresent)
                }
            }
        }
    }

    fun part1(): Int = countBagsWhichCanContainBagOfColour("shiny gold")

    fun countBagsWhichCanContainBagOfColour(colour: String): Int = parseBagMappings().count {
        it.containsBagOfColour(colour)
    }

    fun parseBagMappings(): List<Bag> {
        println ("parsing bag mappings ${lines}")
        val bags = lines.map { parseBagMapping(it)}
        println("Found ${bags.size} bags")
        println(bags)
        bags.forEach { bag ->
            bags.forEach { bag2 ->
                if(bag != bag2) {
                    println("Putting ${bag2.colour} in ${bag.colour} if possible")
                    bag.putIfPresent(bag2)
                }
            }
        }
        println(bags)
        return bags
    }

    fun parseBagMapping(line: String): Bag {
        println(line)
        val(colour, bagListString) = lineRegex.find(line)!!.destructured
        println(colour)
        return Bag(colour, parseBagList(bagListString))
    }

    private fun parseBagList(bagListString: String): MutableMap<Bag, Int> = bagRegex.findAll(bagListString).associate {
        val(count, colour) = it.destructured
        Pair(Bag(colour, mutableMapOf()), count.toInt())
    }.toMutableMap()

}