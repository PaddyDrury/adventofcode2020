package day19

import util.readFile
import util.splitWhen

class Day19(inputFile: String) {
    private val lines: List<String> = readFile(inputFile)
    private val messages = lines.splitWhen(String::isBlank).last()
    private val rules = toRegularExpressionMap(lines.splitWhen(String::isBlank).first())

    fun countMatchesOfRule0(): Int = messages.count { rules[0]!!.matches(it) }

    private fun toRegularExpressionMap(rawRules: List<String>): Map<Int, Regex> {
        val regexMap = mutableMapOf<Int, String>()
        val rawRuleMap = rawRules.map { it.split(": ") }.associate { Pair(it.first().toInt(), it.last()) }

        return rawRuleMap.mapValues { "^(${parseRule(it.key, it.value, rawRuleMap, regexMap, mutableMapOf())})$".toRegex() }
    }

    private fun parseRule(ruleNum: Int, rawRule: String, rawRuleMap: Map<Int, String>, regexMap: MutableMap<Int, String>, recursionDepthMap: MutableMap<Int, Int>): String {
        if(regexMap.contains(ruleNum)) {
            return regexMap[ruleNum]!!
        }
        if(recursionDepthMap.compute(ruleNum) { _, v -> (v ?: 0) + 1 }!! > messages.maxOf(String::length)) return ""
        val rule = when(rawRule) {
            "\"a\"" -> "a"
            "\"b\"" -> "b"
            else -> rawRule.split(" ").map {
                when(val nestedRuleNum = it.toIntOrNull()) {
                    null -> it
                    else -> parseRule(nestedRuleNum, rawRuleMap[nestedRuleNum]!!, rawRuleMap, regexMap, recursionDepthMap)
                }
            }.joinToString("")
        }
        return (if(rule.contains("|")) "($rule)" else rule ).also { regexMap[ruleNum] = it }
    }
}