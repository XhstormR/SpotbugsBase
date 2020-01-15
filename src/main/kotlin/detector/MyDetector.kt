package detector

import edu.umd.cs.findbugs.BugInstance
import edu.umd.cs.findbugs.BugReporter
import edu.umd.cs.findbugs.Priorities
import edu.umd.cs.findbugs.bcel.OpcodeStackDetector
import org.apache.bcel.Const

class MyDetector(private val bugReporter: BugReporter) : OpcodeStackDetector() {

    override fun sawOpcode(seen: Int) {
        if (seen != Const.GETSTATIC.toInt()) return

        // report bug when System.out is used in code
        if (classConstantOperand == "java/lang/System" && nameConstantOperand == "out") {
            val bug = BugInstance(this, "MY_BUG", Priorities.NORMAL_PRIORITY)
                    .addClassAndMethod(this)
                    .addSourceLine(this, pc)
            bugReporter.reportBug(bug)
        }
    }
}
