import java.io.File

fun calculateFifo(input: List<Int>) {
    TODO()
}

class ArgParser(private val args: Array<String>) {
    private fun validateArgs() {
        TODO()
    }
    val inputFile = args[0]
    val fArg = args.indexOf("-f")
    val fFile = if (fArg > -1) args[fArg + 1] else ""
}

fun strToIntList(string: String): List<Int> {
    return string.split(' ').map {
        it.toIntOrNull() ?: throw NumberFormatException("Incorrect input format: integers expected")
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
    val argParser = ArgParser(args)
    val input = parseInput(argParser.inputFile)

}