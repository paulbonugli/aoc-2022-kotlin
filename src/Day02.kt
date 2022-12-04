enum class GameChoice() {
    ROCK(),
    PAPER(),
    SCISSORS()
}

enum class GameOutcome(val score: Int) {
    WIN(6),
    DRAW(3),
    LOSE(0)
}

fun main() {

    fun parseGameChoice(str: String): GameChoice {
        return when (str) {
            "A", "X" -> GameChoice.ROCK
            "B", "Y" -> GameChoice.PAPER
            "C", "Z" -> GameChoice.SCISSORS
            else -> {
                throw IllegalStateException("Unknown game choice $str")
            }
        }
    }

    fun scoreForChoice(choice: GameChoice): Int {
        return choice.ordinal + 1
    }

    fun part1(inputLines: List<String>): Int {
        val inputs = inputLines.map { parseGameChoice(it[0].toString()) to parseGameChoice(it[2].toString()) }

        fun scoreForOutcome(theirChoice: GameChoice, myChoice: GameChoice): Int {
            return when (myChoice) {
                GameChoice.ROCK -> when (theirChoice) {
                    GameChoice.ROCK -> GameOutcome.DRAW
                    GameChoice.PAPER -> GameOutcome.LOSE
                    GameChoice.SCISSORS -> GameOutcome.WIN
                }
                GameChoice.PAPER -> when (theirChoice) {
                    GameChoice.ROCK -> GameOutcome.WIN
                    GameChoice.PAPER -> GameOutcome.DRAW
                    GameChoice.SCISSORS -> GameOutcome.LOSE
                }
                GameChoice.SCISSORS -> when (theirChoice) {
                    GameChoice.ROCK -> GameOutcome.LOSE
                    GameChoice.PAPER -> GameOutcome.WIN
                    GameChoice.SCISSORS -> GameOutcome.DRAW
                }
            }.score
        }

        val scores = inputs.map {
            scoreForChoice(it.second) + scoreForOutcome(it.first, it.second)
        }

        return scores.sum()
    }

    fun part2(inputLines: List<String>): Int {
        fun parseOutcome(str: String): GameOutcome {
            return when (str) {
                "X" -> GameOutcome.LOSE
                "Y" -> GameOutcome.DRAW
                "Z" -> GameOutcome.WIN
                else -> {
                    throw IllegalStateException("Unknown game outcome $str")
                }
            }
        }

        val inputs = inputLines.map { parseGameChoice(it[0].toString()) to parseOutcome(it[2].toString()) }

        fun determineChoice(theirChoice: GameChoice, desiredOutcome: GameOutcome): GameChoice {
            var idx = theirChoice.ordinal
            if (desiredOutcome == GameOutcome.WIN) {
                idx += 1

                // 3 -> 0
                if (idx > 2) {
                    idx -= 3
                }
            } else if (desiredOutcome == GameOutcome.LOSE) {
                idx -= 1

                // -1 -> 2
                if (idx < 0) {
                    idx += 3
                }
            }

            return GameChoice.values()[idx]
        }

        val scores = inputs.map {
            scoreForChoice(determineChoice(it.first, it.second)) + it.second.score
        }

        return scores.sum()
    }


    val lines = readInput("Day02")
    println("Part 1: " + part1(lines))
    println("Part 2: " + part2(lines))
}
