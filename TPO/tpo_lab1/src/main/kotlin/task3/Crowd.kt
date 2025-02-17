package task3

class Crowd {
    private var isJoyful: Boolean = false
    private val people: MutableList<Person> = mutableListOf()
    private val maxCapacity = 100

    fun addPerson(person: Person) {
        require(people.size < maxCapacity) { "Crowd is at maximum capacity" }
        require(!people.contains(person)) { "Person is already in the crowd" }
        people.add(person)
    }

    fun removePerson(person: Person): Boolean {
        return people.remove(person)
    }

    fun getPeopleCount(): Int = people.size

    fun react() {
        require(people.isNotEmpty()) { "Cannot react with empty crowd" }
        isJoyful = true
        people.forEach { it.react() }
    }

    fun shoutJoyfully() {
        require(people.isNotEmpty()) { "Cannot shout with empty crowd" }
        require(isJoyful) { "Crowd must be joyful to shout" }
        people.forEach { it.shoutJoyfully() }
    }

    fun isReacting(): Boolean = isJoyful && people.all { it.isReacting() }

    fun clear() {
        people.clear()
        isJoyful = false
    }
}