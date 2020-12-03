package day2

import java.io.File

class Day2 (private val inputFile: String) {

    private val lines: List<String>
    private val lineRegex = """(\d+)-(\d+) ([a-z]):\s([a-z]+)""".toRegex()

    init {
        lines = readFile(inputFile)
    }

    fun part1(): Int = countValidPasswords(::validatorPart1)
    fun part2(): Int = countValidPasswords(::validatorPart2)
    fun readFile(fileName: String): List<String> = File(fileName).readLines()
    fun countValidPasswords(validator: (Int, Int, String, String) -> Boolean): Int = lines.count { checkPassword(it, validator) }
    fun validatorPart1(minOccurs: Int, maxOccurs: Int, letter: String, password: String): Boolean = """^([^${letter}]*$letter[^$letter]*){$minOccurs,$maxOccurs}${'$'}""".toRegex().matches(password)
    fun validatorPart2(pos1: Int, pos2: Int, letter: String, password: String): Boolean = password[pos1-1].toString().equals(letter).xor(password[pos2-1].toString().equals(letter))
    fun checkPassword(passwordLine: String, validator: (Int, Int, String, String) -> Boolean): Boolean {
        val (num1, num2, letter, password) = lineRegex.find(passwordLine)!!.destructured
        return validator(num1.toInt(), num2.toInt(), letter, password)
    }
}