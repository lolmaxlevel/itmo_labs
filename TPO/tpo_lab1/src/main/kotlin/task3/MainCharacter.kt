package task3

class MainCharacter(private var name: String = "Arthur") {
    private var currentSurface: Surface = Surface.GROUND
    private var isMoving: Boolean = false

    fun slide(newSurface: Surface) {
        require(canMoveToSurface(newSurface)) { "Invalid surface transition" }
        currentSurface = newSurface
        isMoving = true
    }

    private fun canMoveToSurface(newSurface: Surface): Boolean {
        return when (currentSurface) {
            Surface.GROUND -> newSurface != Surface.PLATFORM
            Surface.AIR -> true
            Surface.PLATFORM -> newSurface != Surface.GROUND
        }
    }

    fun getCurrentSurface(): Surface = currentSurface
    fun getName(): String = name
    fun isMoving(): Boolean = isMoving

    fun stopMoving() {
        isMoving = false
    }
}