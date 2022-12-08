fun main() {
    val inputLines = readInput("Day07")

    val root = ElfDir("/")

    // keep track of where we are with traversal
    val traversalPath = ArrayDeque<ElfDir>()

    inputLines.forEach { line ->
        if(line.startsWith("$")) {
            if(line != "$ ls") {
                val (name) = "\\$ cd (/|[a-z]+|..)?".toRegex().matchEntire(line)?.destructured
                                    ?: throw IllegalStateException("unparseable command: $line")

                when (name) {
                    "/" -> traversalPath.run { clear(); add(root) }
                    ".." -> traversalPath.removeLast()
                    else -> traversalPath.add(
                        traversalPath.last().getChildDir(name)
                            ?: throw IllegalStateException("navigating to directory $name which doesn't exist ($line)")
                    )
                }
            }
        } else {
            val (start, name) = "(dir|[0-9]+) ([a-z.]+)".toRegex().matchEntire(line)?.destructured ?: throw IllegalStateException("Unparseable listing: $line")
            val newItem = if(start == "dir") ElfDir(name) else ElfFile(name, start.toInt())

            traversalPath.last().add(newItem)
        }
    }

    println("Inferred filesystem contents:")
    println(root)
    println()

    val nestedDirs = getNestedDirs(root)
    println("""Part 1: ${nestedDirs.map(ElfDir::size).filter { it <= 100000 }.sum()}""")

    val freeSpace = 70000000 - root.size()
    val needToFree = 30000000 - freeSpace
    println("""Part 2: ${nestedDirs.map(ElfDir::size).filter { it >= needToFree }.minOf { it }}""")

}


fun getNestedDirs(vararg startDir : ElfDir) : List<ElfDir>{
    return startDir.flatMap {
        val childDirs = it.getChildDirs()
        childDirs + getNestedDirs(*childDirs.toTypedArray())
    }
}


sealed class ElfFileSystemObject(val name : String) {
    abstract fun size() : Int
}

class ElfFile(name : String, private val size : Int) : ElfFileSystemObject(name) {
    override fun size() : Int{
        return this.size
    }

    override fun toString(): String {
        return "- $name (file, $size)"
    }
}

class ElfDir(name : String, private val contents: MutableSet<ElfFileSystemObject> = mutableSetOf()) : ElfFileSystemObject(name) {

    fun add(item : ElfFileSystemObject) {
        contents.add(item)
    }

    fun getChildDirs(): List<ElfDir> {
        return contents.filterIsInstance<ElfDir>()
    }

    fun getChildDir(subDirName : String): ElfDir? {
        return getChildDirs().find { it.name == subDirName }
    }

    override fun size(): Int {
        return contents.map(ElfFileSystemObject::size).sum()
    }

    override fun toString(): String {
        return "- $name (dir)\n " + contents.flatMap { it.toString().lines() }.joinToString("\n ")
    }
}