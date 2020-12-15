package day15

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ADay15 {
    @Test
    fun testPart1132() {
        assertThat(Day15(mutableListOf(1,3,2)).part1()).isEqualTo(1)
    }
    @Test
    fun testPart1213() {
        assertThat(Day15(mutableListOf(2, 1, 3)).part1()).isEqualTo(10)
    }
    @Test
    fun testPart1123() {
        assertThat(Day15(mutableListOf(1,2,3)).part1()).isEqualTo(27)
    }
    @Test
    fun testPart1231() {
        assertThat(Day15(mutableListOf(2,3,1)).part1()).isEqualTo(78)
    }
    @Test
    fun testPart1321() {
        assertThat(Day15(mutableListOf(3,2,1)).part1()).isEqualTo(438)
    }
    @Test
    fun testPart1312() {
        assertThat(Day15(mutableListOf(3, 1, 2)).part1()).isEqualTo(1836)
    }
    @Test
    fun part1() {
        assertThat(Day15(mutableListOf(17,1,3,16,19,0)).part1()).isEqualTo(694)
    }
}