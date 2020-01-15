class BadCase {
    fun method() = println("Hello SpotBugs!")
}

class GoodCase {
    fun method() = System.err.println("Hello SpotBugs!")
}
