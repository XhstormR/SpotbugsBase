import edu.umd.cs.findbugs.test.CountMatcher
import edu.umd.cs.findbugs.test.SpotBugsRule
import edu.umd.cs.findbugs.test.matcher.BugInstanceMatcherBuilder
import java.nio.file.Paths
import org.hamcrest.MatcherAssert
import org.junit.Rule
import org.junit.Test

class MyDetectorTest {

    @get:Rule
    val spotbugs = SpotBugsRule()

    @Test
    fun testGoodCase() {
        val path = Paths.get("build/classes/kotlin/test", "GoodCase.class")
        val bugCollection = spotbugs.performAnalysis(path)
        val bugTypeMatcher = BugInstanceMatcherBuilder()
                .bugType("MY_BUG")
                .build()
        MatcherAssert.assertThat(bugCollection, CountMatcher.containsExactly(0, bugTypeMatcher))
    }

    @Test
    fun testBadCase() {
        val path = Paths.get("build/classes/kotlin/test", "BadCase.class")
        val bugCollection = spotbugs.performAnalysis(path)
        val bugTypeMatcher = BugInstanceMatcherBuilder()
                .bugType("MY_BUG")
                .build()
        MatcherAssert.assertThat(bugCollection, CountMatcher.containsExactly(1, bugTypeMatcher))
    }
}
