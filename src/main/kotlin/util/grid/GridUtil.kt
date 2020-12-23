package util.grid

typealias Grid = List<String>

fun Grid.transpose(): Grid = this.indices.map { idx -> String(this.map { it[idx] }.toCharArray()) }
fun Grid.flipHorizontal(): Grid = this.map(String::reversed)
fun Grid.rotate(): Grid = this.transpose().flipHorizontal()
fun Grid.leftSide(): String = this.map { it.first() }.toString()
fun Grid.rightSide(): String = this.map { it.last() }.toString()
fun Grid.top(): String = this.first()
fun Grid.bottom(): String = this.last()
fun Grid.matchesRight(rightHandGrid: Grid) = this.rightSide() == rightHandGrid.leftSide()
fun Grid.matchesBelow(belowGrid: Grid) = this.bottom() == belowGrid.top()
fun Grid.print() = this.forEach(::println)
fun Grid.orientations(): List<Grid> = listOf(
    this,
    this.rotate(),
    this.rotate().rotate(),
    this.rotate().rotate().rotate(),
    this.flipHorizontal(),
    this.flipHorizontal().rotate(),
    this.flipHorizontal().rotate().rotate(),
    this.flipHorizontal().rotate().rotate().rotate(),
)
fun Grid.sides(): List<String> = listOf(this.leftSide(), this.rightSide(), this.top(), this.bottom())
fun Grid.allSidesForAllOrientations(): List<String> = this.orientations().flatMap { it.sides() }
fun List<List<Grid>>.merge(): Grid = this.map { it.mergeHorizontal() }.mergeVertical()
fun List<Grid>.mergeHorizontal(): Grid = this.reduce { acc, it -> acc.zip(it).map { it.first + it.second } }
fun List<Grid>.mergeVertical() = this.reduce { acc, it -> acc + it }
fun Grid.count(char: Char) = this.sumOf { it.count { it == char } }
fun Grid.removeBorder(borderThickness: Int = 1) = this.drop(borderThickness).dropLast(borderThickness).map { it.substring(borderThickness, it.length - borderThickness) }