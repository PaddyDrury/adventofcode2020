package day04

import util.readFile
import util.chunkWhen

class Day4(inputFile: String) {
    private val lines: List<String> = readFile(inputFile)
    private val detailRegex = """([a-z]{3}):([^\s]+)""".toRegex()

    data class Passport(private val details: Map<String, String>) {
        fun hasRequiredFields(): Boolean = details.keys.containsAll(
                listOf(
                        "byr",
                        "iyr",
                        "eyr",
                        "hgt",
                        "hcl",
                        "ecl",
                        "pid"
                )
        )

        fun hasValidBirthYear(): Boolean = details["byr"]?.toIntOrNull() in 1920..2002
        fun hasValidIssueYear(): Boolean = details["iyr"]?.toIntOrNull() in 2010..2020
        fun hasValidExpirationYear(): Boolean = details["eyr"]?.toIntOrNull() in 2020..2030
        fun hasValidHeight(): Boolean {
            val matchResult = """^([0-9]{2,3})(in|cm)${'$'}""".toRegex().find(details["hgt"] ?: "")
            if (null != matchResult) {
                val (value, unit) = matchResult.destructured
                return when (unit) {
                    "cm" -> value.toIntOrNull() in 150..193
                    "in" -> value.toIntOrNull() in 59..76
                    else -> false
                }
            } else {
                return false
            }
        }

        fun hasValidHairColour(): Boolean = """^#[a-f0-9]{6}${'$'}""".toRegex().matches(details["hcl"] ?: "")
        fun hasValidEyeColour(): Boolean = """^(amb|blu|brn|gry|grn|hzl|oth)${'$'}""".toRegex().matches(details["ecl"]
                ?: "")

        fun hasValidPassportId(): Boolean = """^[0-9]{9}${'$'}""".toRegex().matches(details["pid"] ?: "")

        fun isValid() = hasRequiredFields() &&
                hasValidBirthYear() &&
                hasValidIssueYear() &&
                hasValidExpirationYear() &&
                hasValidHeight() &&
                hasValidEyeColour() &&
                hasValidHairColour() &&
                hasValidPassportId()
    }


    fun part1(): Int = passports().count { it.hasRequiredFields() }
    fun part2(): Int = passports().count { it.isValid() }
    fun passports(): List<Passport> = lines.chunkWhen { it.isBlank() }.map {
        it.joinToString(separator = " ")
    }.map {
        detailsFromLine(it)
    }.map {
        Passport(it)
    }

    fun detailsFromLine(line: String): Map<String, String> = detailRegex.findAll(line).associate {
        val (key, value) = it.destructured
        Pair(key, value)
    }
}