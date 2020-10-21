import org.junit.jupiter.api.*
import kotlin.IllegalArgumentException
import kotlin.test.assertFailsWith

/**
 * Executes unit tests; Has to be called manually
 */
class ValidationTests {
    @Test
    fun `program args illegal argument`() {
        assertFailsWith<IllegalArgumentException> { ProgramArgs(arrayOf()) }
        assertFailsWith<IllegalArgumentException> { ProgramArgs(arrayOf("doesNotExist.txt")) }
        assertFailsWith<IllegalArgumentException> { ProgramArgs(arrayOf("-s")) }
    }

    @Test
    fun `input parser illegal argument`() {
        assertFailsWith<IllegalArgumentException> { parseInput("src/test/resources/illegal-test.in") }
        assertFailsWith<IllegalArgumentException> { parseInput("src/test/resources/illegal-test2.in") }
    }

    @Test
    fun `valid input parser argument`() {
        assertDoesNotThrow { parseInput("src/test/resources/valid-test.in") }
    }
}