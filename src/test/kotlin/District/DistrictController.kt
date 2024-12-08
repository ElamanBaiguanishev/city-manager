import district.DistrictController
import district.DistrictRepository
import district.DistrictService
import district.dto.CreateDistrictDto
import district.dto.UpdateDistrictDto
import org.junit.jupiter.api.*
import java.io.File
import kotlin.test.*
import kotlin.test.Test

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DistrictControllerTest {

    private lateinit var tempFile: File
    private lateinit var controller: DistrictController

    @BeforeAll
    fun setup() {
        tempFile = File.createTempFile("test_district_controller", ".json")
        val repository = DistrictRepository(tempFile.absolutePath)
        val service = DistrictService(repository)
        controller = DistrictController(service)
    }

    @BeforeEach
    fun clearFile() {
        tempFile.writeText("") // Очищаем содержимое файла перед каждым тестом
    }

    @AfterAll
    fun teardown() {
        tempFile.delete()
    }

    @Test
    fun `should create a new district`() {
        val createDto = CreateDistrictDto("Central District")
        val district = controller.createDistrict(createDto)

        assertNotNull(district.id)
        assertEquals("Central District", district.name)

        val retrievedDistrict = controller.getDistrictById(district.id)
        assertEquals(district.id, retrievedDistrict.id)
        assertEquals(district.name, retrievedDistrict.name)
        assertEquals(district.streets, retrievedDistrict.streets)
    }

    @Test
    fun `should retrieve district by ID`() {
        val createDto = CreateDistrictDto("Another District")
        val district = controller.createDistrict(createDto)

        val retrievedDistrict = controller.getDistrictById(district.id)
        assertEquals(district.id, retrievedDistrict.id)
        assertEquals(district.name, retrievedDistrict.name)
        assertEquals(district.streets, retrievedDistrict.streets)
    }

    @Test
    fun `should update existing district`() {
        val createDto = CreateDistrictDto("Old District")
        val district = controller.createDistrict(createDto)

        val updateDto = UpdateDistrictDto(district.id, "Updated District")
        val updatedDistrict = controller.updateDistrict(updateDto)

        assertEquals("Updated District", updatedDistrict.name)

        val retrievedDistrict = controller.getDistrictById(district.id)
        assertEquals(updatedDistrict.id, retrievedDistrict.id)
        assertEquals(updatedDistrict.name, retrievedDistrict.name)
        assertEquals(updatedDistrict.streets, retrievedDistrict.streets)
    }

    @Test
    fun `should delete district by ID`() {
        val createDto = CreateDistrictDto("District to Delete")
        val district = controller.createDistrict(createDto)

        val result = controller.deleteDistrictById(district.id)
        assertTrue(result)

        val exception = assertThrows<base.NotFoundException> {
            controller.getDistrictById(district.id)
        }
        assertEquals("Округ с ${district.id} не найден", exception.message)
    }

    @Test
    fun `should return all districts`() {
        controller.createDistrict(CreateDistrictDto("District"))
        controller.createDistrict(CreateDistrictDto("DistrictSecond"))

        val allDistricts = controller.getAllDistricts()
        assertEquals(2, allDistricts.size)
        assertTrue(allDistricts.any { it.name == "District" })
        assertTrue(allDistricts.any { it.name == "DistrictSecond" })
    }
}
