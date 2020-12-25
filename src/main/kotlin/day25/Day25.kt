package day25

data class Day25(val doorPublicKey: Long, val cardPublicKey: Long) {

    fun part1(): Long = encryptionKeyFor(cardPublicKey, loopSizeFor(doorPublicKey))

    fun loopSizeFor(publicKey: Long): Long {
        var value = 1L
        var loops = 0L
        do {
            value = transformValue(value, 7)
            loops++
        } while(value != publicKey)
        return loops
    }

    fun encryptionKeyFor(subjectNumber: Long, loopSize: Long): Long {
        var value = 1L
        var count = 0L
        while(count++ < loopSize) {
            value = transformValue(value, subjectNumber)
        }
        return value
    }

    fun transformValue(value: Long, subjectNumber: Long): Long = (value * subjectNumber) % 20201227
}