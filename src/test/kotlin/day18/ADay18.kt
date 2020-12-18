package day18

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ADay18 {
    @Test
    fun testCasesLeftToRightPrecedence() {
        assertThat(LeftToRightOperatorPrecedenceExpressionEvaluator().evaluateExpression("1 + 2 * 3 + 4 * 5 + 6")).isEqualTo(71)
        assertThat(LeftToRightOperatorPrecedenceExpressionEvaluator().evaluateExpression("2 * 3 + (4 * 5)")).isEqualTo(26)
        assertThat(LeftToRightOperatorPrecedenceExpressionEvaluator().evaluateExpression("5 + (8 * 3 + 9 + 3 * 4 * 3)")).isEqualTo(437)
        assertThat(LeftToRightOperatorPrecedenceExpressionEvaluator().evaluateExpression("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))")).isEqualTo(12240)
        assertThat(LeftToRightOperatorPrecedenceExpressionEvaluator().evaluateExpression("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2")).isEqualTo(13632)
    }

    @Test
    fun testCasesPlusPrecedence() {
        assertThat(PlusOperatorPrecedenceExpressionEvaluator().evaluateExpression("1 + 2 * 3 + 4 * 5 + 6")).isEqualTo(231)
        assertThat(PlusOperatorPrecedenceExpressionEvaluator().evaluateExpression("2 * 3 + (4 * 5)")).isEqualTo(46)
        assertThat(PlusOperatorPrecedenceExpressionEvaluator().evaluateExpression("5 + (8 * 3 + 9 + 3 * 4 * 3)")).isEqualTo(1445)
        assertThat(PlusOperatorPrecedenceExpressionEvaluator().evaluateExpression("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))")).isEqualTo(669060)
        assertThat(PlusOperatorPrecedenceExpressionEvaluator().evaluateExpression("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2")).isEqualTo(23340)
    }

    @Test
    fun part1() {
        assertThat(Day18("inputFiles/day18/input.txt").part1()).isEqualTo(11004703763391)
    }

    @Test
    fun part2() {
        assertThat(Day18("inputFiles/day18/input.txt").part2()).isEqualTo(290726428573651)
    }
}