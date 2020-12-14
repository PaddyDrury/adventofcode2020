package day14

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ADay14 {
    @Test
    fun testMasks() {
        assertThat("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X".toMasks().applyTo(11)).isEqualTo(73)
    }

    @Test
    fun testPart1() {
        assertThat(Day14("inputFiles/day14/testinput.txt").part1()).isEqualTo(165)
    }

    @Test
    fun part1() {
        assertThat(Day14("inputFiles/day14/input.txt").part1()).isEqualTo(13496669152158)
    }

    @Test
    fun testPart2() {
        assertThat(Day14("inputFiles/day14/testinput2.txt").part2()).isEqualTo(208)
    }

    @Test
    fun part2() {
        assertThat(Day14("inputFiles/day14/input.txt").part2()).isEqualTo(3278997609887)
    }

    @Test
    fun testGetPossibleValues() {
        assertThat("000000000000000000000000000000X1001X".toFloatingBitsMasks().findPossibleValuesfor(42)).contains(26, 27, 58, 59)
    }
}