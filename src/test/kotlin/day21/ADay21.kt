package day21

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ADay21 {

    @Test
    fun testPart1() {
        assertThat(Day21("inputFiles/day21/testinput.txt").part1()).isEqualTo(5)
    }

    @Test
    fun part1() {
        assertThat(Day21("inputFiles/day21/input.txt").part1()).isEqualTo(2262)
    }
    @Test
    fun part2() {
        assertThat(Day21("inputFiles/day21/input.txt").part2()).isEqualTo("cxsvdm,glf,rsbxb,xbnmzr,txdmlzd,vlblq,mtnh,mptbpz")
    }

}