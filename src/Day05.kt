typealias Stacks = Array<ArrayDeque<Char>>
fun makeStacks(lines : List<String>) : Stacks {
    var stackLines = lines.takeWhile { it.isNotBlank() }
    val numStacks = stackLines.last().split(" ").last().toInt()
    stackLines = stackLines.dropLast(1)

    val stacks : Stacks = Array(numStacks) { ArrayDeque(stackLines.size) }

    stackLines.reversed().forEach{
        it
            .chunked(4)
            .map {pos ->
                "([A-Z])".toRegex().find(pos)?.value?.first()
            }
            .withIndex()
            .forEach { indexedValue ->
                indexedValue.value?.let { ch -> stacks[indexedValue.index].add(ch) }
            }
    }

    return stacks
}

fun printStacks(stacks : Stacks) {
    stacks.forEach(::println)
}

fun getMsg(stacks : Stacks) : String {
    return String(stacks.mapNotNull { it.lastOrNull() }.toCharArray())
}

fun main() {
    val inputLines = readInput("Day05")

    val stacksPart1 = makeStacks(inputLines)
    val stacksPart2 = makeStacks(inputLines)

    val moves = inputLines.mapNotNull(Move::newMove)
    
    // part 1
    moves.forEach {move ->
        repeat(move.numToMove) {
            stacksPart1[move.toStack].add(stacksPart1[move.fromStack].removeLast())
        }
    }

    // part 2
    moves.forEach {move ->
        val cratesToMove = (1..move.numToMove).map { stacksPart2[move.fromStack].removeLast() }
        stacksPart2[move.toStack].addAll(cratesToMove.reversed())
    }

    println("Part 1: ${getMsg(stacksPart1)}")
    println("Part 2: ${getMsg(stacksPart2)}")
}

data class Move(val numToMove: Int, val fromStack: Int, val toStack: Int) {
    companion object {
        fun newMove(input: String) : Move? {
            "^move (\\d+) from (\\d) to (\\d)"
                .toRegex()
                .matchEntire(input)
                ?.let { val (numToMove, fromStack, toStack) = it.destructured
                    return Move(numToMove.toInt(), fromStack.toInt()-1, toStack.toInt()-1)
                }

            return null
        }
    }
}