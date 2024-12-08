import district.District
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class DistrictTest {

    @Test
    fun `should create district with default values`() {
        val district = District(name = "Central District")

        assertNotNull(district.id) // ID автоматически генерируется
        assertEquals("Central District", district.name)
        assertEquals(emptyList<String>(), district.streets)
        assertNotNull(district.createdAt) // Дата должна быть установлена
        assertNotNull(district.updatedAt) // Дата должна быть установлена
    }

    @Test
    fun `should update district streets`() {
        val district = District(name = "Central District")
        district.streets.add("Street-1")
        district.streets.add("Street-2")

        assertEquals(2, district.streets.size)
        assertEquals(listOf("Street-1", "Street-2"), district.streets)
    }
}
