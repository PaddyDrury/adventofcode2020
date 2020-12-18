package day18

import util.readFile

class Day18(inputFile: String) {
    private val lines: List<String> = readFile(inputFile)

    fun part1(): Long = lines.map { LeftToRightOperatorPrecedenceExpressionEvaluator().evaluateExpression(it) }.sum()
    fun part2(): Long = lines.map { PlusOperatorPrecedenceExpressionEvaluator().evaluateExpression(it) }.sum()
}

abstract class ExpressionEvaluator {
    fun evaluateExpression(expression: String): Long = doEvaluateExpression(expression.replace(" ", ""))

    private fun doEvaluateExpression(expression: String): Long {
        val operands = ArrayDeque<Long>()
        var (firstOperand, index) = getNextOperand(expression, 0)
        operands.add(firstOperand)
        while (index < expression.length) {
            val operator = expression[index++]
            val (operand, indexDelta) = getNextOperand(expression, index)
            when (operator) {
                '*' -> doMultiply(operands, operand)
                '+' -> doAddition(operands, operand)
            }
            index += indexDelta
        }
        return doFinalise(operands)
    }

    abstract fun doFinalise(operands: ArrayDeque<Long>): Long
    abstract fun doAddition(operands: ArrayDeque<Long>, operand: Long)
    abstract fun doMultiply(operands: ArrayDeque<Long>, operand: Long)

    private fun getNextOperand(expression: String, index: Int): Pair<Long, Int> = if (expression[index].isDigit()) Pair(expression[index].toString().toLong(), 1) else evaluateNestedExpression(expression, index)

    private fun evaluateNestedExpression(expression: String, index: Int): Pair<Long, Int> = getNestedExpression(expression, index).let { Pair(doEvaluateExpression(it), it.length + 2) }

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
    override fun doFinalise(deque: ArrayDeque<Long>): Long = deque.first()
    override fun doAddition(deque: ArrayDeque<Long>, operand: Long) { deque.add(operand + deque.removeLast()) }
    override fun doMultiply(deque: ArrayDeque<Long>, operand: Long) { deque.add(operand * deque.removeLast()) }
}

class PlusOperatorPrecedenceExpressionEvaluator : ExpressionEvaluator() {
    override fun doFinalise(deque: ArrayDeque<Long>): Long = deque.fold(1) { acc, l -> acc * l }
    override fun doAddition(deque: ArrayDeque<Long>, operand: Long) { deque.add(operand + deque.removeLast()) }
    override fun doMultiply(deque: ArrayDeque<Long>, operand: Long) = deque.addLast(operand)
}