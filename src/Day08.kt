class Forest(private val trees : List<List<Int>>) {

    private val maxX : Int = trees.first().size-1
    private val maxY : Int = trees.size-1

    private fun getSurroundings(x : Int, y : Int) : Array<List<Int>> {
        val left = trees[y].slice(0 until x).reversed()
        val right = trees[y].slice((x + 1)..maxX)
        val up = trees.map { it[x] }.slice(0 until y).reversed()
        val below = trees.map { it[x] }.slice((y+1) .. maxY)

        return arrayOf(left,right,up,below)
    }
    private fun isPositionVisible(x : Int, y : Int) : Boolean {
        val tree = trees[y][x]
        return getSurroundings(x,y).any{ direction -> direction.all { it < tree }}
    }

    private fun getScenicScore(x : Int, y : Int) : Int {
        val tree = trees[y][x]

        return getSurroundings(x, y)
            .map { direction -> direction.takeWhileInclusive { it < tree }.count() }
            .reduce { multiple, item -> multiple * item }
    }

    fun getMostScenicScore() : Int {
        return allPositions().map { (x,y) -> getScenicScore(x, y) }.max()
    }

    private fun allPositions() : List<Pair<Int,Int>> {
        return (0..maxY).flatMap { y ->
            (0..maxX).map { x -> x to y }
        }
    }

    fun countVisible() : Int {
        return allPositions().map { (x,y) -> isPositionVisible(x, y)}.count { (it) }
    }

    fun printVisible() {
        print((0..maxY).joinToString("") { y ->
            (0..maxX).map { x -> isPositionVisible(x, y) }.joinToString("") { if (it) "🎄" else "🟦" } + "\n"
        })
    }
}

fun main() {
    val input = readInput("Day08").map {
        it.toList().map(Char::digitToInt)
    }
    val forest = Forest(input)

    println()

    forest.printVisible()
    println("${forest.countVisible()} trees visible \uD83C\uDF84")
    println("Most scenic score: ${forest.getMostScenicScore()}")
}

fun <T> Iterable<T>.takeWhileInclusive(predicate: (T) -> Boolean): List<T> {
    val list = ArrayList<T>()
    for (item in this) {
        list.add(item)

        // moved below list.add vs. takeWhile implementation
        if (!predicate(item))
            break
    }
    return list
}
