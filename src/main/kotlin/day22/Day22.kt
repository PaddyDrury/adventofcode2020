package day22

import util.readFile
import util.splitWhen

typealias Deck = List<Int>

class Day22(inputFile: String) {
    private val lines = readFile(inputFile)
    private val deck1 = lines.splitWhen(String::isBlank).first().drop(1).map(String::toInt).toMutableList()
    private val deck2 = lines.splitWhen(String::isBlank).last().drop(1).map(String::toInt).toMutableList()

    fun part1(): Int = gameOfCombat(deck1, deck2) { card1, _, card2, _ ->
        whoWinsRoundNormal(card1, card2)
    }.second.reversed().reduceIndexed { index, acc, value ->
        acc + (value * (index + 1))
    }

    fun part2(): Int = gameOfCombat(deck1, deck2, ::whoWinsRoundRecursive)
        .second.reversed().reduceIndexed { index, acc, value ->
            acc + (value * (index + 1))
        }

    fun gameOfCombat(deck1: Deck, deck2: Deck, whoWins: (Int, Deck, Int, Deck) -> Player): Pair<Player, Deck> {
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
            when (whoWins(player1Card, player1Deck, player2Card, player2Deck)) {
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

    private fun whoWinsRoundRecursive(card1: Int, deck1: Deck, card2: Int, deck2: Deck): Player =
        if (deck1.size >= card1 && deck2.size >= card2) gameOfCombat(
            deck1.take(card1),
            deck2.take(card2),
            ::whoWinsRoundRecursive
        ).first else whoWinsRoundNormal(card1, card2)

    private fun whoWinsRoundNormal(card1: Int, card2: Int): Player =
        if (card1 > card2) Player.PLAYER_1 else Player.PLAYER_2
}

enum class Player {
    PLAYER_1,
    PLAYER_2
}