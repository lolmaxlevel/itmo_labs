package task3

class Orator(private val name: String = "Orator") {
    private var currentSurface: Surface = Surface.PLATFORM
    private var isSpeaking: Boolean = false

    fun moveTo(surface: Surface) {
        require(surface != Surface.AIR) { "Orator cannot move to AIR" }
        currentSurface = surface
    }

    fun speak(crowd: Crowd) {
        require(crowd.getPeopleCount() > 0) { "Cannot speak to empty crowd" }
        require(currentSurface == Surface.PLATFORM) { "Orator must be on PLATFORM to speak" }
        isSpeaking = true
        crowd.react()
    }

    fun stopSpeaking() {
        isSpeaking = false
    }

    fun getCurrentPosition(): Surface = currentSurface
    fun getName(): String = name
    fun isSpeaking(): Boolean = isSpeaking
}