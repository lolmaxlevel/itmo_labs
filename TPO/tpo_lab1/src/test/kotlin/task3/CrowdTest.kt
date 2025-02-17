// CrowdTest.kt
package task3

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.*

class CrowdTest {
    private lateinit var crowd: Crowd
    private val initialPeople = List(10) { index -> Person("Person$index") }

    @BeforeEach
    fun setup() {
        crowd = Crowd()
        initialPeople.forEach { crowd.addPerson(it) }
    }

    @Test
    fun `test adding duplicate person throws exception`() {
        val person = initialPeople.first()
        assertFailsWith<IllegalArgumentException> {
            crowd.addPerson(person)
        }
    }

    @Test
    fun `test removing non-existent person returns false`() {
        val nonExistentPerson = Person("NonExistent")
        assertFalse(crowd.removePerson(nonExistentPerson))
    }

    @Test
    fun `test crowd at maximum capacity`() {
        val crowd = Crowd()
        repeat(100) { index ->
            crowd.addPerson(Person("Person$index"))
        }

        assertFailsWith<IllegalArgumentException> {
            crowd.addPerson(Person("OneMore"))
        }
    }

    @Test
    fun `test empty crowd operations`() {
        val emptyCrowd = Crowd()

        assertFailsWith<IllegalArgumentException> {
            emptyCrowd.react()
        }

        assertFailsWith<IllegalArgumentException> {
            emptyCrowd.shoutJoyfully()
        }
    }

    @Test
    fun `test shouting without being joyful`() {
        assertFailsWith<IllegalArgumentException> {
            crowd.shoutJoyfully()
        }
    }

    @Test
    fun `test clearing crowd`() {
        crowd.clear()
        assertEquals(0, crowd.getPeopleCount())
        assertFalse(crowd.isReacting())
    }

    @Test
    fun `test normal operations after clear`() {
        crowd.clear()
        val person = Person("NewPerson")
        crowd.addPerson(person)
        assertEquals(1, crowd.getPeopleCount())
    }
}