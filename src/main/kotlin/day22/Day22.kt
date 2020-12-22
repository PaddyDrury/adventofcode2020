package day22

import util.readFile
import util.splitWhen

typealias Deck = List<Int>

class Day22(inputFile: String) {
    private val lines = readFile(inputFile)
    private val deck1 = lines.splitWhen(String::isBlank).first().drop(1).map(String::toInt).toMutableList()
    private val deck2 = lines.splitWhen(String::isBlank).last().drop(1).map(String::toInt).toMutableList()

    fun part1(): Int {
        while (deck1.isNotEmpty() && deck2.isNotEmpty()) {
            val player1Card = deck1.removeFirst()
            val player2Card = deck2.removeFirst()
            if (player1Card > player2Card) {
                deck1.add(player1Card)
                deck1.add(player2Card)
            } else {
                deck2.add(player2Card)
                deck2.add(player1Card)
            }
        }
        return (if (deck1.isNotEmpty()) deck1 else deck2).reversed().reduceIndexed { index, acc, value ->
            acc + (value * (index + 1))
        }
    }

    fun part2(): Int = recursiveCombat(deck1, deck2).also { println(it) }.second.reversed().reduceIndexed { index, acc, value ->
        acc + (value * (index + 1))
    }

    fun recursiveCombat(deck1: Deck, deck2: Deck): Pair<Player, Deck> {
        val previousDecks = mutableListOf<Pair<Deck, Deck>>()
        val player1Deck = deck1.toMutableList()
        val player2Deck = deck2.toMutableList()
        while (player1Deck.isNotEmpty() && player2Deck.isNotEmpty()) {
            if (previousDecks.contains(Pair(player1Deck, player2Deck))) {
                return Pair(Player.PLAYER_1, player1Deck)
            }
            previousDecks.add(Pair(player1Deck.toList(), player2Deck.toList()))
            val player1Card = player1Deck.removeFirst()
            val player2Card = player2Deck.removeFirst()
            when (whoWinsRound(player1Card, player1Deck, player2Card, player2Deck)) {
                Player.PLAYER_1 -> {
                    player1Deck.add(player1Card)
                    player1Deck.add(player2Card)
                }
                Player.PLAYER_2 -> {
                    player2Deck.add(player2Card)
                    player2Deck.add(player1Card)
                }
            }
        }
        return if (player1Deck.isNotEmpty()) Pair(Player.PLAYER_1, player1Deck) else Pair(Player.PLAYER_2, player2Deck)
    }

    fun whoWinsRound(card1: Int, deck1: Deck, card2: Int, deck2: Deck): Player {
        if (deck1.size >= card1 && deck2.size >= card2) {
            return recursiveCombat(deck1.take(card1), deck2.take(card2)).first
        }
        return if (card1 > card2) Player.PLAYER_1 else Player.PLAYER_2
    }
}

enum class Player {
    PLAYER_1,
    PLAYER_2
}