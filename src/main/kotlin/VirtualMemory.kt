import java.io.File
import kotlin.IllegalArgumentException
import java.util.Queue
import java.util.LinkedList

fun calculateFifo(input: List<Int>, ram: Int, pages: Int) {
    //TODO: compare queue implementations
    val queue: Queue<Int> = LinkedList()
    val inRam = Array<Boolean>(pages) {false}
    var countReplacements = 0
    for (page in input) {
        if (!inRam[page]) {
            if (queue.size == pages) {
                val firstIn = queue.remove()
                inRam[firstIn] = false
                countReplacements++
            }
            queue.add(page)
            inRam[page] = true
        }
    }
}

fun calculateLru(input: List<Int>, ram: Int, pages: Int) {
    val set = sortedSetOf<Int>()
    val inRam = Array<Boolean>(pages) {false}
    val lastUsed = Array<Int>(pages) {0}
    var countReplacements = 0
    for ((index, page) in input.withIndex()) {
        if (!inRam[page]) {
            if (set.size == pages) {
                val lastUsedPage = set.first()
                set.remove(set.first())
                inRam[input[lastUsedPage]] = false
                set.add(index)
                inRam[page] = true
                countReplacements++
            }
        }
        else{
            set.remove(lastUsed[page])
            set.add(index)
        }
        lastUsed[page] = index
    }
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
    File(fileName).useLines { return it.toList() }
}

fun handleOutput(){
    TODO()
}

fun main(args: Array<String>) {
    //val a: List<Int> = s.split(' ').map { it.toInt() }
    //val argParser = ProgramArgs(args)
    //xval input = parseInput(argParser.inputFile)
    val set2 = sortedSetOf<Int>()
}