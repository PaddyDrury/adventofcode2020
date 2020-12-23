package day22

import util.chunkWhen
import util.readFile

typealias Deck = List<Int>

class Day22(inputFile: String) {
    private val lines = readFile(inputFile)
    private val deck1 = lines.chunkWhen(String::isBlank).first().drop(1).map(String::toInt).toMutableList()
    private val deck2 = lines.chunkWhen(String::isBlank).last().drop(1).map(String::toInt).toMutableList()

    fun part1(): Int = playCombat(deck1, deck2) { card1, _, card2, _ ->
        whoWinsRoundNormal(card1, card2)
    }.second.reversed().reduceIndexed { index, acc, value ->
        acc + (value * (index + 1))
    }

    fun part2(): Int = playCombat(deck1, deck2, ::whoWinsRoundRecursive)
        .second.reversed().reduceIndexed { index, acc, value ->
            acc + (value * (index + 1))
        }

    fun playCombat(deck1: Deck, deck2: Deck, whoWins: (Int, Deck, Int, Deck) -> Player): Pair<Player, Deck> {
        val history = mutableListOf<Pair<Deck, Deck>>()
        val player1Deck = deck1.toMutableList()
        val player2Deck = deck2.toMutableList()
        while (player1Deck.isNotEmpty() && player2Deck.isNotEmpty()) {
            if (history.contains(player1Deck to player2Deck)) {
                return Player.PLAYER_1 to player1Deck
            }
            history.add(player1Deck.toList() to player2Deck.toList())
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
        return if (player1Deck.isNotEmpty()) Player.PLAYER_1 to player1Deck else Player.PLAYER_2 to player2Deck
    }

    private fun whoWinsRoundRecursive(card1: Int, deck1: Deck, card2: Int, deck2: Deck): Player =
        if (deck1.size >= card1 && deck2.size >= card2) playCombat(
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