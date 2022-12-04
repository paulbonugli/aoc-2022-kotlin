fun main() {
    fun determineRange(range: String): IntRange {
        val splits = range.split("-").map { it.toInt() }
        return IntRange(splits[0], splits[1])
    }

    fun parseInput(input: List<String>): List<Pair<IntRange, IntRange>> {
        return input.map { it.split(",") }.map {
            determineRange(it[0]) to determineRange(it[1])
        }
    }

    fun part1(input: List<String>): Int {
        return parseInput(input).count { pair ->
            pair.first.all {
                pair.second.contains(it)
            } or pair.second.all {
                pair.first.contains(it)
            }
        }
    }

    fun part2(input: List<String>): Int {
        return parseInput(input).count { pair ->
            pair.first.any {
                pair.second.contains(it)
            } or pair.second.any {
                pair.first.contains(it)
            }
        }
    }

    val lines = readInput("Day04")
    println("Part 1: " + part1(lines))
    println("Part 2: " + part2(lines))
}
