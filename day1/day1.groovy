#!/usr/bin/env groovy
import org.paukov.combinatorics3.Generator


@Grab(group = 'com.github.dpaukov', module = 'combinatoricslib3', version = '3.3.0')


def sumOfElementsEquals(list, value) {
    list.sum { it } == value
}

def readIntsFromFile(file) {
    (file as File).readLines().collect { it.toInteger() }
}

def findCombinationSummingToValue(list, combolength, value) {
    Generator.combination(list)
            .multi(combolength)
            .find {sumOfElementsEquals(it, value)}
}

def findProduct(list) {
    list.inject(1) { result, i -> result * i }
}

def part1(file) {
    findProduct(findCombinationSummingToValue(readIntsFromFile(file), 2, 2020))
}

def part2(file) {
    findProduct(findCombinationSummingToValue(readIntsFromFile(file), 3, 2020))
}

assert sumOfElementsEquals([2019, 1], 2020)

def testCombination = findCombinationSummingToValue(readIntsFromFile('testinput.txt'), 2, 2020)

assert testCombination.size() == 2
assert testCombination.contains(1721)
assert testCombination.contains(299)
assert findProduct(testCombination) == 514579
assert part1('testinput.txt') == 514579
assert part2('testinput.txt') == 241861950

println part1('input.txt')
println part2('input.txt')