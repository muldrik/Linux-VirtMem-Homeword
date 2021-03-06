import org.junit.jupiter.api.*
import java.lang.IllegalArgumentException
import java.time.Duration
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/**
 * Executes unit tests; Has to be called manually
 */
class OptTests {
    @Test
    fun `simple test`() {
        val input1 = listOf<Int>(1, 2, 3, 4, 5, 1, 2, 3)
        val expected1 = listOf<Int>(1, 2, 3, 3, 3, -1, -1, 1)
        assertEquals(QueryAnswer(expected1, 6), calculateOpt(input1, 3, 5))

        val input2 = listOf<Int>(1, 1, 1, 2, 2, 2, 1, 1, 1)
        val expected2 = listOf<Int>(1, -1, -1, 1, -1, -1, 1, -1, -1)
        assertEquals(QueryAnswer(expected2, 3), calculateOpt(input2, 1, 2))

        val input3 = listOf<Int>(1, 2, 3, 1, 4, 5, 6)
        val expected3 = listOf<Int>(1, 2, 3, -1, 1, 2, 3)
        assertEquals(QueryAnswer(expected3, 6), calculateOpt(input3, 3, 6))
    }

    @Test
    fun `timeout test`() {
        val result = assertTimeoutPreemptively(Duration.ofSeconds(3)) {
            val queries = parseInput("src/test/resources/example_1.in")
            for (query in queries) {
                calculateOpt(query.inputSequence, query.ramSize, query.pages)
            }
        }
    }
}