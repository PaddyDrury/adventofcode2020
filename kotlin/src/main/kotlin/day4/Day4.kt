package day4

import com.sun.org.apache.xpath.internal.operations.Bool
import java.io.File
import java.util.function.Predicate

class Day4(private val inputFile: String) {
    private val lines: List<String>
    private val detailRegex = """([a-z]{3}):([^\s]+)""".toRegex()

    init {
        lines = readFile(inputFile)
    }

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
            var matchResult =  """^([0-9]{2,3})(in|cm)${'$'}""".toRegex().find(details["hgt"] ?: "")
            if(null != matchResult) {
                val(value, unit) = matchResult.destructured
                when(unit) {
                    "cm" -> return value.toIntOrNull() in 150..193
                    "in" -> return value.toIntOrNull() in 59..76
                    else -> return false
                }
            } else {
                return false
            }
        }
        fun hasValidHairColour(): Boolean = """^#[a-f0-9]{6}${'$'}""".toRegex().matches(details["hcl"] ?: "")
        fun hasValidEyeColour(): Boolean = """^(amb|blu|brn|gry|grn|hzl|oth)${'$'}""".toRegex().matches(details["ecl"] ?: "")
        fun hasValidPassportId(): Boolean ="""^[0-9]{9}${'$'}""".toRegex().matches(details["pid"] ?: "")

        fun isValid() = hasRequiredFields() &&
                hasValidBirthYear() &&
                hasValidIssueYear() &&
                hasValidExpirationYear() &&
                hasValidHeight() &&
                hasValidEyeColour() &&
                hasValidHairColour() &&
                hasValidPassportId()
    }
    fun readFile(fileName: String): List<String> = File(fileName).readLines()

    fun part1(): Int = passports().count { it.hasRequiredFields() }
    fun part2(): Int = passports().count { it.isValid() }
    fun passports(): List<Passport> = lines.splitWhen { it.isBlank() }.map {
        it.joinToString(separator = " ")
    }.map {
        detailsFromLine(it)
    }.map {
        Passport(it)
    }
    fun detailsFromLine(line: String): Map<String, String> = detailRegex.findAll(line).associate {
        var (key, value) = it!!.destructured
        Pair(key, value)
    }
}

fun <T> List<T>.splitWhen(condition: Predicate<T>): List<List<T>> = this.fold(mutableListOf(mutableListOf<T>())) { acc, t ->
    if (condition.test(t)) {
        acc.add(mutableListOf())
    } else {
        acc.last().add(t)
    }
    acc
}.filter {
    !it.isEmpty()
}