import district.District
import district.DistrictRepository
import org.junit.jupiter.api.*
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DistrictRepositoryTest {
    private lateinit var tempFile: File
    private lateinit var repository: DistrictRepository

    @BeforeAll
    fun setup() {
        tempFile = File.createTempFile("test_district_repo", ".json")
        repository = DistrictRepository(tempFile.absolutePath)
    }

    @AfterAll
    fun `tear down`() {
        tempFile.delete()
    }

    @Test
    fun `should save and retrieve a district`() {
        val district = District(name = "Central District")
        repository.save(district)

        val retrieved = repository.findById(district.id)
        assertNotNull(retrieved)
        assertEquals(district.id, retrieved.id)
        assertEquals("Central District", retrieved.name)
    }

    @Test
    fun `should find all districts`() {
        repository.save(District(name = "District 1"))
        repository.save(District(name = "District 2"))

        val allDistricts = repository.findAll()
        assertEquals(2, allDistricts.size)
    }

    @Test
    fun `should update a district`() {
        val district = District(name = "Old Name")
        repository.save(district)

        val updatedDistrict = district.copy(name = "New Name")
        repository.update(updatedDistrict)

        val retrieved = repository.findById(district.id)
        assertNotNull(retrieved)
        assertEquals("New Name", retrieved.name)
    }

    @Test
    fun `should delete a district by id`() {
        val district = District(name = "To Delete")
        repository.save(district)

        val deleted = repository.deleteById(district.id)
        assertEquals(true, deleted)

        val retrieved = repository.findById(district.id)
        assertNull(retrieved)
    }
}
