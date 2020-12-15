package util

import org.paukov.combinatorics3.Generator
import java.util.function.Predicate

fun <T> Iterable<T>.splitWhen(condition: Predicate<T>): List<List<T>> = this.fold(mutableListOf(mutableListOf<T>())) { acc, t ->
    if (condition.test(t)) {
        acc.add(mutableListOf())
    } else {
        acc.last().add(t)
    }
    acc
}.filter {
    !it.isEmpty()
}

fun <T> Collection<T>.combinations(length: Int): Iterable<List<T>> = Generator.combination(this).multi(length)

fun Iterable<Int>.sumsToValue(value: Int): Boolean = this.sum() == value

fun Iterable<Long>.sumsToValue(value: Long): Boolean = this.sum() == value

fun Iterable<Long>.addMinToMax(): Long = this.minOrNull()!! + this.maxOrNull()!!

fun <T> List<T>.sublistBetweenMinAndMaxOf(values: Iterable<Int>): List<T> = this.subList(values.minOrNull()!!, values.maxOrNull()!!)

fun Iterable<Long>.subtractMinFromMax(): Long = this.maxOrNull()!! - this.minOrNull()!!