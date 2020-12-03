#!/usr/bin/env groovy

assert part1('testinput.txt') == 7
println part1('input.txt')

assert part2('testinput.txt') == 336
println part2('input.txt')

def countTrees(grid, tree, slope) {
    grid.indexed().findResults { idx, line ->
        idx % slope.down == 0 ? line : null
    }.inject([column: 0, treeCount: 0]) { state, line ->
        if (line.charAt(state.column % line.length()) == tree) {
            state.treeCount++
        }
        state.column += slope.right
        state
    }.treeCount
}

def part1(input) {
    countTrees((input as File).readLines(), '#', [right: 3, down: 1])
}

def part2(input) {
    def grid = (input as File).readLines()
    [
            [right: 1, down: 1],
            [right: 3, down: 1],
            [right: 5, down: 1],
            [right: 7, down: 1],
            [right: 1, down: 2]
    ].inject(1L) { result, slope ->
        result * countTrees(grid, '#', slope)
    }
}
