import java.util.Queue
import java.util.LinkedList

/**
 * The result of an algorithm - sequence of operations and number of replacements
 */
data class QueryAnswer(val operations: List<Int>, val replacements: Int)

/**
 * Calculate First In First Out
 * N = input.size
 * Time: O(N)
 * Space: O(N)
 */
fun calculateFifo(input: List<Int>, ramSize: Int, pages: Int): QueryAnswer {
    val requests = input.size
    val operations = MutableList(requests) {-1}
    val elementsInRam: Queue<Int> = LinkedList() // Elements currently in RAM
    val indexInRam = Array<Int>(pages+1) {0} // To quickly check where an element is in ram (0 if it isn't)
    var countReplacements = 0
    for ((index, page) in input.withIndex()) {
        if (indexInRam[page]==0) {
            var frameToSwap = countReplacements+1 // If there is free space than only countReplacements pages have been used
            if (elementsInRam.size == ramSize) { // If a replacement is required, remove First In element
                val firstIn = elementsInRam.remove()
                frameToSwap = indexInRam[firstIn]
                indexInRam[firstIn] = 0
            }
            operations[index] = frameToSwap
            countReplacements++
            elementsInRam.add(page)
            indexInRam[page] = frameToSwap
        }
    }
    return QueryAnswer(operations, countReplacements)
}

/**
 * Calculate Least Recently Used
 * N = input.size
 * Time: O(N*log(N))
 * Space: O(N)
 */
fun calculateLru(input: List<Int>, ramSize: Int, pages: Int): QueryAnswer {
    val requests = input.size
    val operations = MutableList(requests) {-1}
    val set = sortedSetOf<Int>() // Page numbers of unique pages that are in Ram sorted by time of last use
    val indexInRam = Array<Int>(pages+1) {0} // To quickly check where an element is in ram (0 if it isn't)
    val lastUsed = Array<Int>(pages+1) {0} // When was a page last accessed
    var countReplacements = 0
    for ((index, page) in input.withIndex()) {
        if (indexInRam[page]==0) {
            var frameToSwap = countReplacements+1 // If there is free space than only countReplacements pages have been used
            if (set.size == ramSize) { // If a replacement is required, replace the least recently used page
                val lastUsedPageIndex = set.first()
                set.remove(set.first())
                frameToSwap = indexInRam[input[lastUsedPageIndex]]
                indexInRam[input[lastUsedPageIndex]] = 0
            }
            operations[index] = frameToSwap
            countReplacements++
            indexInRam[page] = frameToSwap
        }
        else{
            set.remove(lastUsed[page])
        }
        set.add(index)
        lastUsed[page] = index
    }
    return QueryAnswer(operations, countReplacements)
}

/**
 * Pair of the page and the next time it will be used
 * Supports sorting in descending order by nextUse, if equal - ascending order by page
 */
data class NextUsagePage(val nextUse: Int, val page: Int): Comparable<NextUsagePage> {
    override fun compareTo(other: NextUsagePage): Int {
        val result = other.nextUse.compareTo(nextUse) // Compare to sort in descending order
        return if (result != 0) result else page.compareTo(other.page)
    }
}

/**
 * Calculate Optimal algorithm
 * N = input.size
 * Time: O(N*log(N))
 * Space: O(N)
 */
fun calculateOpt(input: List<Int>, ramSize: Int, pages: Int): QueryAnswer {
    val requests = input.size
    val operations = MutableList(requests) {-1}
    val inRamNextUse = sortedSetOf<NextUsagePage>() // Pages that are in ram and their nextUse, sorted by nextUse
    val indexInRam = Array<Int>(pages+1) {0} // To quickly check where an element is in ram (0 if it isn't)
    var cntInRam = 0
    val nextRequests = List<Queue<Int>>(pages+1){ LinkedList() } // For each element all future requests to it
    for ((index, page) in input.withIndex()) {
        nextRequests[page].add(index)
    }
    val neverUsedAgain = requests+1
    for (pageNumber in 1..pages) {
        nextRequests[pageNumber].add(neverUsedAgain) // All elements are assumed to have "never again" access time
        nextRequests[pageNumber].remove() // First request to every elements is not considered
    }
    var countReplacements = 0
    for ((index, page) in input.withIndex()) {
        if (indexInRam[page]==0) {
            var frameToSwap = cntInRam+1
            if (cntInRam == ramSize) { // If a replacement is required, replace an element that will be used latest
                val latestUsedPage = inRamNextUse.first()
                inRamNextUse.remove(inRamNextUse.first())
                frameToSwap = indexInRam[latestUsedPage.page]
                indexInRam[latestUsedPage.page] = 0
            }
            else cntInRam++
            operations[index] = frameToSwap
            countReplacements++
            indexInRam[page] = frameToSwap
        }
        // If the accessed element was already in ram, update it's "next access time"
        else inRamNextUse.remove(NextUsagePage(index, page))
        inRamNextUse.add(NextUsagePage(nextRequests[page].remove(), page))
    }
    return QueryAnswer(operations, countReplacements)
}