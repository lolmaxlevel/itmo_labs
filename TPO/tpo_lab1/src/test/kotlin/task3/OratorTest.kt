package task3

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.*

class OratorTest {
    private lateinit var orator: Orator
    private lateinit var crowd: Crowd

    @BeforeEach
    fun setup() {
        orator = Orator("TestOrator")
        crowd = Crowd()
        repeat(5) { crowd.addPerson(Person("Person$it")) }
    }

    @Test
    fun `test initial state`() {
        assertEquals("TestOrator", orator.getName())
        assertEquals(Surface.PLATFORM, orator.getCurrentPosition())
        assertFalse(orator.isSpeaking())
    }

    @Test
    fun `test valid surface movement`() {
        orator.moveTo(Surface.GROUND)
        assertEquals(Surface.GROUND, orator.getCurrentPosition())

        orator.moveTo(Surface.PLATFORM)
        assertEquals(Surface.PLATFORM, orator.getCurrentPosition())
    }

    @Test
    fun `test invalid surface movement`() {
        assertFailsWith<IllegalArgumentException> {
            orator.moveTo(Surface.AIR)
        }
    }

    @Test
    fun `test speaking to crowd`() {
        orator.speak(crowd)
        assertTrue(orator.isSpeaking())
        assertTrue(crowd.isReacting())
    }

    @Test
    fun `test speaking to empty crowd`() {
        val emptyCrowd = Crowd()
        assertFailsWith<IllegalArgumentException> {
            orator.speak(emptyCrowd)
        }
    }

    @Test
    fun `test speaking from wrong position`() {
        orator.moveTo(Surface.GROUND)
        assertFailsWith<IllegalArgumentException> {
            orator.speak(crowd)
        }
    }

    @Test
    fun `test stop speaking`() {
        orator.speak(crowd)
        assertTrue(orator.isSpeaking())

        orator.stopSpeaking()
        assertFalse(orator.isSpeaking())
        assertTrue(crowd.isReacting()) // Crowd should still be reacting
    }
}