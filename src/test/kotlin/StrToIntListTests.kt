import org.junit.jupiter.api.*
import java.io.File
import java.lang.IllegalArgumentException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/**
 * Executes unit tests; Has to be called manually
 */
class StrToIntListTests {
    @Test
    fun `simple test`(){
        assertEquals(listOf(1, 2, 3), strToIntList("1 2 3"))
    }

    @Test
    fun `illegal argument test`(){
        assertFailsWith(IllegalArgumentException::class){
            strToIntList("")
        }
        assertFailsWith(IllegalArgumentException::class){
            strToIntList("1 2 a")
        }
        assertFailsWith(IllegalArgumentException::class){
            strToIntList("1  2")
        }
        assertFailsWith(IllegalArgumentException::class){
            strToIntList(" ")
        }
    }
}