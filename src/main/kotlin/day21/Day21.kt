package day21

import util.readFile

class Day21(inputFile: String) {
    private val lines: List<String> = readFile(inputFile)
    private val menuItemRegex = """^([a-z\s]+) \(contains ([a-z,\s]+)\)${'$'}""".toRegex()

    fun part1(): Int = let { mapAllergensToIngredients().values }
        .let { allergenIngredients ->
            parseMenu()
                .flatMap { food -> food.ingredients }
                .filterNot { allergenIngredients.contains(it) }
                .count()
        }

    fun part2(): String = let { mapAllergensToIngredients() }
        .let { allergenMap -> allergenMap.keys.sorted().map {allergenMap[it]!!}.joinToString(",") }

    fun parseMenu() = lines.map(::parseMenuItem)

    fun parseMenuItem(raw: String): MenuItem {
        val(spaceSeparatedIngredients, commaAndSpaceSeparatedAllergens) = menuItemRegex.matchEntire(raw)!!.destructured
        return MenuItem(spaceSeparatedIngredients.split(" "), commaAndSpaceSeparatedAllergens.split(", "))
    }

    fun mapAllergensToIngredients(): Map<String, String>{
        val menu = parseMenu()
        val allergenToIngredientsMap = menu.flatMap { it.allergens }.toSet().associateWith { allergen ->
            menu.filter { it.allergens.contains(allergen) }
                .map { it.ingredients.toSet() }
                .reduce { acc, ingredients -> acc.intersect(ingredients) }
                .toMutableSet()
        }

         while (allergenToIngredientsMap.values.any { it.size > 1 }) {
             allergenToIngredientsMap.values.filter { it.size == 1 }.forEach { allergenToRemove ->
                 allergenToIngredientsMap.values.filter { it.size > 1 && it.containsAll(allergenToRemove) }.forEach { ingredients ->
                     ingredients.removeAll(allergenToRemove)
                 }
             }
         }

        return allergenToIngredientsMap.mapValues { it.value.first() }
    }
}

data class MenuItem(val ingredients: List<String>, val allergens: List<String>)