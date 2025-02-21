package task3

class MainCharacter(private var name: String = "Arthur") {
    private var currentSurface: Surface = Surface.GROUND
    private var isMoving: Boolean = false
    private var currentWindow: Window? = null

    fun slide(newSurface: Surface) {
        require(canMoveToSurface(newSurface)) { "Invalid surface transition" }
        currentSurface = newSurface
        isMoving = true
        currentWindow = null
    }

    private fun canMoveToSurface(newSurface: Surface): Boolean {
        return when (currentSurface) {
            Surface.GROUND -> newSurface != Surface.PLATFORM
            Surface.AIR -> true
            Surface.PLATFORM -> newSurface != Surface.GROUND
        }
    }

    fun approachWindow(window: Window, building: Building) {
        require(building.getAllWindows().contains(window)) { "Window must belong to the building" }
        require(currentSurface == Surface.PLATFORM || currentSurface == Surface.AIR) {
            "Character must be on platform or in air to approach window"
        }
        currentWindow = window
    }

    fun getCurrentWindow(): Window? = currentWindow
    fun getCurrentSurface(): Surface = currentSurface
    fun getName(): String = name
    fun isMoving(): Boolean = isMoving

    fun stopMoving() {
        isMoving = false
    }
}