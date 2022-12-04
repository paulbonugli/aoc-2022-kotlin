fun main() {
    fun determinePriority(c : Char) : Int {
        return if (c > 'Z') {
            c - 'a' + 1
        } else {
            c - 'A' + 26 + 1
        }
    }

    fun part1(input : List<String>) : Int {
        return input.map { line ->
            val c1 = line.subSequence(0, line.length / 2)
            val c2 = line.subSequence((line.length / 2), line.length)
            c1 to c2
        }.map { pair ->
            pair.first.asIterable().first { pair.second.contains(it) }
        }.sumOf { determinePriority(it) }
    }

    fun part2(input : List<String>) : Int {
        return input.chunked(3).flatMap { group ->
            group.map{ it.toSet() }.reduce{ left, right -> left intersect right}
        }.sumOf { determinePriority(it) }
    }

    val lines = readInput("Day03")
    println("Part 1: " + part1(lines))
    println("Part 2: " + part2(lines))
}
