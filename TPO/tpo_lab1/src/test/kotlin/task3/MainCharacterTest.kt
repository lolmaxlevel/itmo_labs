package task3

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.*

class MainCharacterTest {
    private lateinit var character: MainCharacter
    private lateinit var building: Building

    @BeforeEach
    fun setup() {
        character = MainCharacter()
        building = Building()
    }

    @Test
    fun `test initial state`() {
        assertEquals("Arthur", character.getName())
        assertEquals(Surface.GROUND, character.getCurrentSurface())
        assertFalse(character.isMoving())
    }

    @Test
    fun `test custom name`() {
        val customCharacter = MainCharacter("CustomName")
        assertEquals("CustomName", customCharacter.getName())
    }

    @Test
    fun `test valid surface transitions`() {
        // Ground to Air
        character.slide(Surface.AIR)
        assertEquals(Surface.AIR, character.getCurrentSurface())
        assertTrue(character.isMoving())

        // Air to Platform
        character.slide(Surface.PLATFORM)
        assertEquals(Surface.PLATFORM, character.getCurrentSurface())
        assertTrue(character.isMoving())

        // Platform to Air
        character.slide(Surface.AIR)
        assertEquals(Surface.AIR, character.getCurrentSurface())
        assertTrue(character.isMoving())

        // Air to Ground
        character.slide(Surface.GROUND)
        assertEquals(Surface.GROUND, character.getCurrentSurface())
        assertTrue(character.isMoving())
    }

    @Test
    fun `test invalid surface transitions`() {
        // Ground to Platform
        assertFailsWith<IllegalArgumentException> {
            character.slide(Surface.PLATFORM)
        }

        // Move to Platform via Air
        character.slide(Surface.AIR)
        character.slide(Surface.PLATFORM)

        // Platform to Ground
        assertFailsWith<IllegalArgumentException> {
            character.slide(Surface.GROUND)
        }
    }

    @Test
    fun `test stop moving`() {
        character.slide(Surface.AIR)
        assertTrue(character.isMoving())

        character.stopMoving()
        assertFalse(character.isMoving())
        assertEquals(Surface.AIR, character.getCurrentSurface())
    }

    @Test
    fun `test sliding to same surface`() {
        character.slide(Surface.AIR)
        assertEquals(Surface.AIR, character.getCurrentSurface())

        character.slide(Surface.AIR)
        assertEquals(Surface.AIR, character.getCurrentSurface())
        assertTrue(character.isMoving())
    }

    @Test
    fun `test approach window from platform`() {
        character.slide(Surface.AIR)
        character.slide(Surface.PLATFORM)

        val window = building.getWindowsOnFloor(1)[0]
        character.approachWindow(window, building)

        assertEquals(window, character.getCurrentWindow())
    }

    @Test
    fun `test approach window from air`() {
        character.slide(Surface.AIR)

        val window = building.getWindowsOnFloor(2)[0]
        character.approachWindow(window, building)

        assertEquals(window, character.getCurrentWindow())
    }

    @Test
    fun `test cannot approach window from ground`() {
        val window = building.getWindowsOnFloor(1)[0]

        assertFailsWith<IllegalArgumentException> {
            character.approachWindow(window, building)
        }
    }

    @Test
    fun `test window reset after moving`() {
        character.slide(Surface.AIR)
        val window = building.getWindowsOnFloor(2)[0]
        character.approachWindow(window, building)

        character.slide(Surface.PLATFORM)
        assertNull(character.getCurrentWindow())
    }

    @Test
    fun `test approach window from wrong building`() {
        character.slide(Surface.AIR)
        val wrongBuilding = Building()
        val window = building.getWindowsOnFloor(1)[0]

        assertFailsWith<IllegalArgumentException> {
            character.approachWindow(window, wrongBuilding)
        }
    }
}