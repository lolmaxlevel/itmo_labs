package task3

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.*

class BuildingTest {
    private lateinit var building: Building

    @BeforeEach
    fun setup() {
        building = Building()
    }

    @Test
    fun `test default building properties`() {
        assertEquals(2, building.getFloorCount())
        assertTrue(building.hasWindows())
        assertTrue(building.hasPlatformInFront())
    }

    @Test
    fun `test windows per floor`() {
        val firstFloorWindows = building.getWindowsOnFloor(1)
        val secondFloorWindows = building.getWindowsOnFloor(2)

        assertEquals(2, firstFloorWindows.size)
        assertEquals(2, secondFloorWindows.size)
        assertEquals(4, building.getAllWindows().size)
    }

    @Test
    fun `test invalid floor number`() {
        assertFailsWith<IllegalArgumentException> {
            building.getWindowsOnFloor(0)
        }
        assertFailsWith<IllegalArgumentException> {
            building.getWindowsOnFloor(3)
        }
    }

    @Test
    fun `test building without windows`() {
        val buildingNoWindows = Building(floors = 2, hasWindows = false)
        assertTrue(buildingNoWindows.getAllWindows().isEmpty())
    }

    @Test
    fun `test building without platform`() {
        val buildingNoPlatform = Building(hasPlatform = false)
        assertFalse(buildingNoPlatform.hasPlatformInFront())
    }

    @Test
    fun `test invalid floor count`() {
        assertFailsWith<IllegalArgumentException> {
            Building(floors = 0)
        }
    }

    @Test
    fun `test window operations`() {
        val window = building.getAllWindows().first()
        assertFalse(window.isOpen())

        window.open()
        assertTrue(window.isOpen())

        window.close()
        assertFalse(window.isOpen())
    }

    @Test
    fun `test window floor assignment`() {
        val firstFloorWindows = building.getWindowsOnFloor(1)
        val secondFloorWindows = building.getWindowsOnFloor(2)

        firstFloorWindows.forEach { assertEquals(1, it.floor) }
        secondFloorWindows.forEach { assertEquals(2, it.floor) }
    }
}