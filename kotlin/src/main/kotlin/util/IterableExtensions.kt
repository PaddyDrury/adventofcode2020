package util

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