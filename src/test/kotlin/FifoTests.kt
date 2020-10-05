import org.junit.jupiter.api.*
import java.lang.IllegalArgumentException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/**
 * Executes unit tests; Has to be called manually
 */
class FifoTests {
    @Test
    fun `simple test`(){
        val input1 = listOf<Int>(1, 2, 3, 4, 5, 1, 2, 3)
        val expected1 = listOf<Int>(1, 2, 3, 1, 2, 3, 1, 2)
        assertEquals(QueryAnswer(expected1, 8), calculateFifo(input1, 3, 5))
    }
}