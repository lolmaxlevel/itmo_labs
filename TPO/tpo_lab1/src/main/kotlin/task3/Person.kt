package task3

class Person(
    private val name: String
) {
    private var isJoyful: Boolean = false

    fun react() {
        isJoyful = true
    }

    fun isReacting(): Boolean = isJoyful

    fun getName(): String = name

    fun shoutJoyfully() {
        if (isJoyful) {
            println("$name: Yay!")
        }
    }
}