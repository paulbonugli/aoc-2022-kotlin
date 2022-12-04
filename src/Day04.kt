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

    infix fun IntRange.contains(other: IntRange): Boolean =
        contains(other.first) && contains(other.last)

    infix fun IntRange.overlaps(other: IntRange): Boolean =
        contains(other.first) || contains(other.last) || other.contains(this)

    fun part1(input: List<String>): Int {
        return parseInput(input).count { pair ->
            pair.first.contains(pair.second) || pair.second.contains(pair.first)
        }
    }

    fun part2(input: List<String>): Int {
         return parseInput(input).count { pair ->
            pair.first overlaps pair.second
        }
    }

    val lines = readInput("Day04")
    println("Part 1: " + part1(lines))
    println("Part 2: " + part2(lines))
}
