package task3

class Building(
    private val floors: Int = 2,
    private val hasWindows: Boolean = true,
    private val hasPlatform: Boolean = true
) {
    init {
        require(floors > 0) { "Building must have at least one floor" }
    }

    private val windows: MutableList<Window> = mutableListOf()

    init {
        if (hasWindows) {
            repeat(floors) { floor ->
                repeat(2) {
                    windows.add(Window(floor + 1))
                }
            }
        }
    }

    fun getWindowsOnFloor(floor: Int): List<Window> {
        require(floor in 1..floors) { "Invalid floor number" }
        return windows.filter { it.floor == floor }
    }

    fun getFloorCount(): Int = floors
    fun hasPlatformInFront(): Boolean = hasPlatform
    fun hasWindows(): Boolean = hasWindows
    fun getAllWindows(): List<Window> = windows.toList()
}

class Window(val floor: Int) {
    private var isOpen: Boolean = false

    fun open() {
        isOpen = true
    }

    fun close() {
        isOpen = false
    }

    fun isOpen(): Boolean = isOpen
}