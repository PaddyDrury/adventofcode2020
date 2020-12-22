package day22

import util.readFile
import util.splitWhen

class Day22(inputFile: String) {
    private val lines = readFile(inputFile)
    private val deck1 = ArrayDeque(lines.splitWhen(String::isBlank).first().drop(1).map(String::toLong))
    private val deck2 = ArrayDeque(lines.splitWhen(String::isBlank).last().drop(1).map(String::toLong))

    fun part1(): Long {
        while(deck1.isNotEmpty() && deck2.isNotEmpty()) {
            val player1Card = deck1.removeFirst()
            val player2Card = deck2.removeFirst()
            if(player1Card > player2Card) {
                deck1.addLast(player1Card)
                deck1.addLast(player2Card)
            } else {
                deck2.addLast(player2Card)
                deck2.addLast(player1Card)
            }
        }
        return (if(deck1.isNotEmpty()) deck1 else deck2).reversed().reduceIndexed {index, acc, value ->
            acc + (value * (index+1))
        }
    }
}