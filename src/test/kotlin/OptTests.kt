import org.junit.jupiter.api.*
import java.lang.IllegalArgumentException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/**
 * Executes unit tests; Has to be called manually
 */
class OptTests {
    @Test
    fun `simple test`() {

        val input1 = listOf<Int>(1, 2, 3, 4, 5, 1, 2, 3)
        val expected1 = listOf<Int>(-1, -1, -1, 3, 4, -1, -1, 1)
        assertEquals(QueryAnswer(expected1, 3), calculateOpt(input1, 3, 5))

        val input2 = listOf<Int>(1, 1, 1, 2, 2, 2, 1, 1, 1)
        val expected2 = listOf<Int>(-1, -1, -1, 1, -1, -1, 2, -1, -1)
        assertEquals(QueryAnswer(expected2, 2), calculateOpt(input2, 1, 2))

        val input3 = listOf<Int>(1, 2, 3, 1, 4, 5, 6)
        val expected3 = listOf<Int>(-1, -1, -1, -1, 1, 2, 3)
        assertEquals(QueryAnswer(expected3, 3), calculateOpt(input3, 3, 6))
    }

    @Test
    fun `illegal argument test`() {

    }
}