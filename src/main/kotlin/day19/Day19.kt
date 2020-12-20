package day19

import util.readFile
import util.splitWhen

class Day19(inputFile: String) {
    private val lines: List<String> = readFile(inputFile)
    private val messages = lines.splitWhen(String::isBlank).last()
    private val rules = lines.splitWhen(String::isBlank).first()
        .map { it.split(": ") }
        .associate { Pair(it.first().toInt(), it.last()) }
        .let { raw ->
            val regexCache = mutableMapOf<Int, String>()
            raw.mapValues { v -> "^(${parseRule(v.key, raw, regexCache, mutableMapOf())})$".toRegex() }
        }

    fun countMatchesOfRule0(): Int = messages.count { rules[0]!!.matches(it) }

    private fun parseRule(
        ruleNum: Int,
        raw: Map<Int, String>,
        regexCache: MutableMap<Int, String>,
        depthCache: MutableMap<Int, Int>
    ): String {
        if (regexCache.contains(ruleNum)) return regexCache[ruleNum]!!
        if (depthCache.compute(ruleNum) { _, v -> (v ?: 0) + 1 }!! >= messages.maxOf(String::length)) return ""
        val rule = when (raw[ruleNum]!!) {
            "\"a\"" -> "a"
            "\"b\"" -> "b"
            else -> raw[ruleNum]!!.split(" ").map {
                when (val nestedRuleNum = it.toIntOrNull()) {
                    null -> it
                    else -> parseRule(nestedRuleNum, raw, regexCache, depthCache)
                }
            }.joinToString("")
        }
        return (if (rule.contains("|")) "($rule)" else rule).also { regexCache[ruleNum] = it }
    }
}