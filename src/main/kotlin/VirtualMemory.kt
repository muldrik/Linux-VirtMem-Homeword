import java.io.File
import kotlin.IllegalArgumentException

data class AlgoComparison(val fifoResult: QueryAnswer, val lruResult: QueryAnswer, val optResult: QueryAnswer)

data class QueryInput(val pages: Int, val ramSize: Int, val inputSequence: List<Int>)

fun compareAlgorithms(input: QueryInput): AlgoComparison {
    val fifoResult = calculateFifo(input.inputSequence, input.ramSize, input.pages)
    val lruResult = calculateLru(input.inputSequence, input.ramSize, input.pages)
    val optResult = calculateOpt(input.inputSequence, input.ramSize, input.pages)
    return AlgoComparison(fifoResult, lruResult, optResult)
}

fun processPackage(queries: List<QueryInput>): List<AlgoComparison> {
    val algoComparisons = mutableListOf<AlgoComparison>()
    for (query in queries) {
        algoComparisons.add(compareAlgorithms(query))
    }
    return algoComparisons
}

fun strToIntList(string: String): List<Int> {
    if (string.isEmpty()) throw IllegalArgumentException("Input cannot be empty")
    return string.split(' ').map {
        it.toIntOrNull() ?: throw IllegalArgumentException("Incorrect input format: integers expected, instead found '$it'")
    }
}

fun parseInput(fileName: String) : List<QueryInput> {
    val rawInput: List<String> = File(fileName).readLines()
    val processedInput = mutableListOf<QueryInput>()
    if (rawInput.size % 2 != 0) throw IllegalArgumentException("Incorrect input format")
    var index = 0
    while (index<rawInput.size) {
        val memoryParams = strToIntList(rawInput[index])
        if (memoryParams.size != 2) throw IllegalArgumentException("Incorrect input format. Number of pages ans ram size expected")
        val pages = memoryParams[0]
        val ramSize = memoryParams[1]
        val inputSequence = strToIntList(rawInput[index+1])
        processedInput.add(QueryInput(pages, ramSize, inputSequence))
        index += 2
    }
    return processedInput
}

fun handleOutput(algoComparisons: List<AlgoComparison>, sArg: Boolean){
    for (comparison in algoComparisons) {
        if (!sArg) {
            println(comparison.fifoResult.operations.joinToString(separator = " "))
            println(comparison.lruResult.operations.joinToString(separator = " "))
            println(comparison.optResult.operations.joinToString(separator = " "))
        }
        println("${comparison.fifoResult.replacements} ${comparison.lruResult.replacements} ${comparison.optResult.replacements}")
    }
}

class ProgramArgs(args: Array<String>) {
    private fun validateArgs(args: Array<String>) {
        if (args.isEmpty()) throw IllegalArgumentException("Input file expected")
    }

    init {
        validateArgs(args)
    }

    val inputFile = args[0]
    val sArg: Boolean = args.contains("-s")
}

fun main(args: Array<String>) {
    val programArgs = ProgramArgs(args)
    val input = parseInput(programArgs.inputFile)
    val output = processPackage(input)
    handleOutput(output, programArgs.sArg)
}