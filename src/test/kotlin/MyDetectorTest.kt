import edu.umd.cs.findbugs.test.SpotBugsExtension
import edu.umd.cs.findbugs.test.SpotBugsRunner
import java.nio.file.Paths
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SpotBugsExtension::class)
class MyDetectorTest {

    @Test
    fun testGoodCase(spotbugs: SpotBugsRunner) {
        val path = Paths.get("build/classes/kotlin/test", "GoodCase.class")
        val bugs = spotbugs.performAnalysis(path)

        Assertions.assertEquals(0, bugs.collection.size)
    }

    @Test
    fun testBadCase(spotbugs: SpotBugsRunner) {
        val path = Paths.get("build/classes/kotlin/test", "BadCase.class")
        val bugs = spotbugs.performAnalysis(path)

        Assertions.assertEquals(1, bugs.collection.size)
        Assertions.assertTrue(bugs.collection.map { it.type }.contains("MY_BUG"))
    }
}
