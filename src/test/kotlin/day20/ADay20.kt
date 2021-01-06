package day20

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ADay20 {

    @Test
    fun testPart1() {
        assertThat(Day20("inputFiles/day20/input.txt").part1()).isEqualTo(14986175499719L)
    }

    @Test
    fun testPart2() {
        assertThat(Day20("inputFiles/day20/input.txt").part2()).isEqualTo(2161)
    }
}