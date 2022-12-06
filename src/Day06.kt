private enum class SignalMarker(val numDistinctChars: Int) {
    START_OF_PACKET(4),
    START_OF_MESSAGE(14);

    fun isValidMarker(input: String) : Boolean {
        return input.toCharArray().toSet().size == numDistinctChars
    }

    fun findMarker(input: String) : Int {
        return input
            .windowed(numDistinctChars)
            .takeWhile {
                !isValidMarker(it)
            }
            .count() + numDistinctChars
    }
}

fun main() {
    readInput("Day06").forEach {
        println("Part 1: " + SignalMarker.START_OF_PACKET.findMarker(it))
        println("Part 2: " + SignalMarker.START_OF_MESSAGE.findMarker(it))
    }
}
