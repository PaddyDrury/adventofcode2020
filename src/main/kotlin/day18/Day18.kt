package day18

import util.readFile

class Day18(inputFile: String) {
    private val lines: List<String> = readFile(inputFile)

    fun part1(): Long = lines.map { LeftToRightOperatorPrecedenceExpressionEvaluator().evaluateExpression(it) }.sum()
    fun part2(): Long = lines.map { PlusOperatorPrecedenceExpressionEvaluator().evaluateExpression(it) }.sum()
}

abstract class ExpressionEvaluator {
    fun evaluateExpression(expression: String): Long = doEvaluateExpression(expression.replace(" ", ""))

    abstract fun doEvaluateExpression(expression: String): Long

    fun getNextOperand(expression: String, index: Int): Pair<Long, Int> = if (expression[index].isDigit()) Pair(expression[index].toString().toLong(), 1) else evaluateNestedExpression(expression, index)

    private fun evaluateNestedExpression(expression: String, index: Int): Pair<Long, Int> {
        val nestedExpression = getNestedExpression(expression, index)
        return Pair(doEvaluateExpression(nestedExpression), nestedExpression.length + 2)
    }

    private fun getNestedExpression(expression: String, index: Int): String {
        var unbalancedParentheses = 0
        var currIndex = index
        do {
            when (expression[currIndex++]) {
                '(' -> unbalancedParentheses++
                ')' -> unbalancedParentheses--
            }
        } while (unbalancedParentheses > 0)
        return expression.substring(index + 1, currIndex - 1)
    }
}

class LeftToRightOperatorPrecedenceExpressionEvaluator : ExpressionEvaluator() {
    override fun doEvaluateExpression(expression: String): Long {
        var (total, index) = getNextOperand(expression, 0)
        while (index < expression.length) {
            val operator = expression[index++]
            val (operand, indexDelta) = getNextOperand(expression, index)
            when (operator) {
                '*' -> total *= operand
                '+' -> total += operand
            }
            index += indexDelta
        }
        return total
    }
}

class PlusOperatorPrecedenceExpressionEvaluator : ExpressionEvaluator() {
    override fun doEvaluateExpression(expression: String): Long {
        val deque = ArrayDeque<Long>()
        var (firstOperand, index) = getNextOperand(expression, 0)
        deque.add(firstOperand)
        while (index < expression.length) {
            val operator = expression[index++]
            val (operand, indexDelta) = getNextOperand(expression, index)
            when (operator) {
                '*' -> deque.addLast(operand)
                '+' -> deque.add(operand + deque.removeLast())
            }
            index += indexDelta
        }
        return deque.fold(1) { acc, l -> acc * l }
    }
}