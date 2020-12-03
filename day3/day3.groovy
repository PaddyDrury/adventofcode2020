#!/usr/bin/env groovy

assert part1('testinput.txt') == 7
println part1('input.txt')

assert part2('testinput.txt') == 336
println part2('input.txt')

class TripState {
  def column, row, treeCount
}

def countTrees(grid, tree, down,right) {
    grid.inject(new TripState(column: 0, row: 0, treeCount: 0)) { state, line ->
        if(state.row % down == 0) {
            if(line.charAt(state.column % line.length()) == tree) {
                state.treeCount++
            }
            state.column += right
        }
        state.row++
        state
    }.treeCount
}

def part1(input) {
    countTrees((input as File).readLines(), '#', 1, 3)
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
        result * countTrees(grid, '#', slope.down, slope.right)
    }
}
