package day19

import util.readFile
import util.splitWhen

class Day19(inputFile: String) {
    private val lines: List<String> = readFile(inputFile)
    private val messages = lines.splitWhen(String::isBlank).last()

    fun part1(): Int = toRules(lines.splitWhen(String::isBlank).first()).let { rules -> messages.count { rules[0]!!.matches(it) } }
    fun part2(): Int = toRules(lines.splitWhen(String::isBlank).first().map {
        when {
            it.startsWith("8:") -> "8: 42 | 42 8"
            it.startsWith("11:") -> "11: 42 31 | 42 11 31"
            else -> it
        }
    }).let { rules -> messages.count { rules[0]!!.matches(it) } }

    private fun toRules(ruleLines: List<String>): Map<Int, Regex> = ruleLines
        .map { it.split(": ") }
        .associate { Pair(it.first().toInt(), it.last()) }
        .let { raw ->
            val regexCache = mutableMapOf<Int, String>()
            raw.mapValues { v -> "^(${parseRule(v.key, raw, regexCache, mutableMapOf())})$".toRegex() }
        }

    private fun parseRule(
        id: Int,
        raw: Map<Int, String>,
        regexCache: MutableMap<Int, String>,
        depthCache: MutableMap<Int, Int>
    ): String {
        if (regexCache.contains(id)) return regexCache[id]!!
        if (depthCache.compute(id) { _, v -> (v ?: 0) + 1 }!! >= messages.maxOf(String::length)) return ""
        val rule = when (raw[id]!!) {
            "\"a\"" -> "a"
            "\"b\"" -> "b"
            else -> raw[id]!!.split(" ").map {
                when (it) {
                    "|" -> it
                    else -> parseRule(it.toInt(), raw, regexCache, depthCache)
                }
            }.joinToString("")
        }
        return (if (rule.contains("|")) "($rule)" else rule).also { regexCache[id] = it }
    }
}