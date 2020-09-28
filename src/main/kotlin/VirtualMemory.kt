import java.io.File
import kotlin.IllegalArgumentException
import java.util.Queue
import java.util.LinkedList

data class QueryAnswer(val operations: List<Int>, val replacements: Int)

fun calculateFifo(input: List<Int>, ramSize: Int, pages: Int): QueryAnswer {
    //TODO: compare queue implementations
    val requests = input.size
    val operations = MutableList(requests) {-1}
    val queue: Queue<Int> = LinkedList()
    val inRam = Array<Boolean>(pages+1) {false}
    var countReplacements = 0
    for ((index, page) in input.withIndex()) {
        if (!inRam[page]) {
            if (queue.size == ramSize) {
                val firstIn = queue.remove()
                inRam[firstIn] = false
                operations[index] = firstIn
                countReplacements++
            }
            queue.add(page)
            inRam[page] = true
        }
    }
    return QueryAnswer(operations, countReplacements)
}

data class NextUsagePage(val nextUse: Int, val page: Int): Comparable<NextUsagePage> {
    override fun compareTo(other: NextUsagePage): Int {
        val result = other.nextUse.compareTo(nextUse) // Compare to sort in descending order
        return if (result != 0) result else page.compareTo(other.page)
    }
}

fun calculateLru(input: List<Int>, ramSize: Int, pages: Int): QueryAnswer {
    val requests = input.size
    val operations = MutableList(requests) {-1}
    val set = sortedSetOf<Int>()
    val isInRam = Array<Boolean>(pages+1) {false}
    val lastUsed = Array<Int>(pages+1) {0}
    var countReplacements = 0
    for ((index, page) in input.withIndex()) {
        if (!isInRam[page]) {
            if (set.size == ramSize) {
                val lastUsedPage = set.first()
                set.remove(set.first())
                isInRam[input[lastUsedPage]] = false
                operations[index] = input[lastUsedPage]
                countReplacements++
            }
            isInRam[page] = true
        }
        else{
            set.remove(lastUsed[page])
        }
        set.add(index)
        lastUsed[page] = index
    }
    return QueryAnswer(operations, countReplacements)
}



fun calculateOpt(input: List<Int>, ramSize: Int, pages: Int): QueryAnswer {
    val requests = input.size
    val operations = MutableList(requests) {-1}
    val set = sortedSetOf<NextUsagePage>()
    val inRam = Array<Boolean>(pages+1) {false}
    var cntInRam = 0
    val nextRequests = List<Queue<Int>>(pages+1){ LinkedList() }
    for ((index, page) in input.withIndex()) {
        nextRequests[page].add(index)
    }
    val neverUsedAgain = requests+1
    for (pageNumber in 1..pages) {
        nextRequests[pageNumber].add(neverUsedAgain)
        nextRequests[pageNumber].remove()
    }
    var countReplacements = 0
    for ((index, page) in input.withIndex()) {
        if (!inRam[page]) {
            if (cntInRam == ramSize) {
                val latestUsedPage = set.first()
                set.remove(set.first())
                inRam[latestUsedPage.page] = false
                operations[index] = latestUsedPage.page
                countReplacements++
            }
            else cntInRam++
            inRam[page] = true
        }
        else set.remove(NextUsagePage(index, page))
        set.add(NextUsagePage(nextRequests[page].remove(), page))
    }
    return QueryAnswer(operations, countReplacements)
}

class ProgramArgs(args: Array<String>) {
    private fun validateArgs(args: Array<String>) {
        TODO()
    }

    init {
        validateArgs(args)
    }

    val inputFile = args[0]
    val fArg = args.indexOf("-f")
    val fFile = if (fArg > -1) args[fArg + 1] else ""
}

fun strToIntList(string: String): List<Int> {
    if (string.isEmpty()) throw IllegalArgumentException("Input cannot be empty")
    return string.split(' ').map {
        it.toIntOrNull() ?: throw IllegalArgumentException("Incorrect input format: integers expected, instead found '$it'")
    }
}

fun parseInput(fileName: String) : List<String> {
    return File(fileName).readLines()
}

fun handleOutput(){
    TODO()
}

fun main(args: Array<String>) {
    //val a: List<Int> = s.split(' ').map { it.toInt() }
    //val argParser = ProgramArgs(args)
    //val input = parseInput(argParser.inputFile)
    val input1 = listOf<Int>(1, 2, 3, 1, 4, 5, 6)
    val expected1 = listOf<Int>(-1, -1, -1, 1, 2, 3, 4, 5)
    print(calculateOpt(input1, 3, 6))
}