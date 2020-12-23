package day16

import util.readFile
import util.chunkWhen

class Day16(inputFile: String) {
    private val lines: List<String> = readFile(inputFile)
    private val fieldRegex = """^([a-z\s]+): (\d+)-(\d+) or (\d+)-(\d+)${'$'}""".toRegex()

    fun part1() = parseNotes().let { notes ->
        notes.nearbyTickets.map { ticket ->
            ticket.findFieldsWhichAreNotValidForAny(notes.fieldDefinitions)
        }.sumOf { it.sum() }
    }

    fun part2() = parseNotes().let { notes ->
        val fieldPositions = notes.findFieldPositions()
        notes.fieldDefinitions.filter {
            it.name.startsWith("departure")
        }.map {
            notes.myTicket.fields[fieldPositions[it]!!]
        }.fold(1L) { acc, i -> acc * i }
    }

    fun parseNotes(): Notes = lines.chunkWhen(String::isBlank).let { Notes(it.first().map(::parseField), parseTicket(it[1][1]), it.last().drop(1).map(::parseTicket)) }

    fun parseField(string: String): FieldDefinition {
        val (name, lo1, hi1, lo2, hi2) = fieldRegex.matchEntire(string)!!.destructured
        return FieldDefinition(name, lo1.toInt()..hi1.toInt(), lo2.toInt()..hi2.toInt())
    }

    fun parseTicket(string: String): Ticket = Ticket(string.split(',').map(String::toInt))
}

data class FieldDefinition(val name: String, val range1: IntRange, val range2: IntRange) {
    fun validate(value: Int): Boolean = value in range1 || value in range2
}

data class Ticket(val fields: List<Int>) {
    fun findFieldsWhichAreNotValidForAny(fieldDefinitions: List<FieldDefinition>): List<Int> = fields.filter { field -> fieldDefinitions.none { definition -> definition.validate(field) } }
}

data class Notes(val fieldDefinitions: List<FieldDefinition>, val myTicket: Ticket, val nearbyTickets: List<Ticket>) {
    fun findFieldPositions(): Map<FieldDefinition, Int> {
        val validTickets = validTickets()

        val possibleFieldPositions = fieldDefinitions.associateWith { fieldDefinition ->
            (myTicket.fields.indices).filter { pos ->
                validTickets.map { ticket -> ticket.fields[pos] }.all(fieldDefinition::validate)
            }.toMutableSet()
        }

        while (possibleFieldPositions.values.any { it.size > 1 }) {
            possibleFieldPositions.values.filter { it.size == 1 }.forEach { positionsToRemove ->
                possibleFieldPositions.values.filter { it.size > 1 }.forEach { positions -> positions.removeAll(positionsToRemove) }
            }
        }

        return possibleFieldPositions.mapValues { it.value.first() }
    }

    private fun validTickets(): List<Ticket> = (nearbyTickets + myTicket).filter { it.findFieldsWhichAreNotValidForAny(fieldDefinitions).isEmpty() }
}