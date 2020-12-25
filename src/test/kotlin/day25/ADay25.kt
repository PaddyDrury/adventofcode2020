package day25

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ADay25 {
    @Test
    fun testGetLoopCount1() {
        assertThat(Day25(0,0).loopSizeFor(5764801)).isEqualTo(8)
    }

    @Test
    fun testGetLoopCount2() {
        assertThat(Day25(0,0).loopSizeFor(17807724)).isEqualTo(11)
    }

    @Test
    fun testGetEncryptionKey1() {
        assertThat(Day25(0,0).encryptionKeyFor(17807724, 8)).isEqualTo(14897079)
    }

    @Test
    fun testGetEncryptionKey2() {
        assertThat(Day25(0,0).encryptionKeyFor(5764801, 11)).isEqualTo(14897079)
    }

    @Test
    fun testPart1() {
        assertThat(Day25(5764801, 17807724).part1()).isEqualTo(14897079)
    }

    @Test
    fun part1() {
        assertThat(Day25(8252394, 6269621).part1()).isEqualTo(181800)
        assertThat(Day25(6269621, 8252394).part1()).isEqualTo(181800)
    }
}