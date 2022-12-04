import java.io.File

fun main() {
    val fileContent = File("src/Day01.txt").readText()
    val elves = fileContent.split("\n\n").map {
        it.lines().sumOf { calories -> calories.toInt() }
    }

    println("Part 1: " + elves.max())
    println("Part 2: " + elves.sortedDescending().take(3).sum())
}
