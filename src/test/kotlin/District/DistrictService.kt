import district.DistrictRepository
import district.DistrictService
import district.dto.CreateDistrictDto
import district.dto.UpdateDistrictDto
import org.junit.jupiter.api.*
import java.io.File
import kotlin.test.*
import kotlin.test.Test

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DistrictServiceTest {

    private lateinit var tempFile: File
    private lateinit var service: DistrictService

    @BeforeAll
    fun setup() {
        // Создаем временный файл для хранения данных
        tempFile = File.createTempFile("test_district_service", ".json")
        service = DistrictService(DistrictRepository(tempFile.absolutePath))
    }

    @AfterAll
    fun teardown() {
        tempFile.delete()
    }

    @Test
    fun `should add a new district`() {
        val createDto = CreateDistrictDto("Central District")
        val addedDistrict = service.addDistrict(createDto)

        assertNotNull(addedDistrict.id)
        assertEquals("Central District", addedDistrict.name)

        val retrievedDistrict = service.getDistrictById(addedDistrict.id)

        // Сравниваем только значимые свойства
        assertEquals(addedDistrict.id, retrievedDistrict.id)
        assertEquals(addedDistrict.name, retrievedDistrict.name)
        assertEquals(addedDistrict.streets, retrievedDistrict.streets)
    }


    @Test
    fun `should retrieve district by ID`() {
        val createDto = CreateDistrictDto("Another District")
        val addedDistrict = service.addDistrict(createDto)

        val retrievedDistrict = service.getDistrictById(addedDistrict.id)
        assertEquals(addedDistrict.id, retrievedDistrict.id)
        assertEquals(addedDistrict.name, retrievedDistrict.name)
        assertEquals(addedDistrict.streets, retrievedDistrict.streets)
    }

    @Test
    fun `should throw exception when retrieving non-existent district`() {
        val nonExistentId = "123e4567-e89b-12d3-a456-426614174000"

        val exception = assertThrows<base.NotFoundException> {
            service.getDistrictById(nonExistentId)
        }
        assertEquals("Округ с $nonExistentId не найден", exception.message)
    }

    @Test
    fun `should update existing district`() {
        val createDto = CreateDistrictDto("Old District")
        val addedDistrict = service.addDistrict(createDto)

        val updateDto = UpdateDistrictDto(addedDistrict.id, "Updated District")
        val updatedDistrict = service.updateDistrict(updateDto)

        val retrievedDistrict = service.getDistrictById(updatedDistrict.id)

        // Проверяем поля вместо сравнения объектов
        assertEquals(updatedDistrict.id, retrievedDistrict.id)
        assertEquals("Updated District", retrievedDistrict.name)
        assertEquals(updatedDistrict.streets, retrievedDistrict.streets)
    }


    @Test
    fun `should throw exception when updating non-existent district`() {
        val nonExistentId = "123e4567-e89b-12d3-a456-426614174000"
        val updateDto = UpdateDistrictDto(nonExistentId, "Non-existent District")

        val exception = assertThrows<base.NotFoundException> {
            service.updateDistrict(updateDto)
        }
        assertEquals("Округ с $nonExistentId не найден", exception.message)
    }

    @Test
    fun `should delete district by ID`() {
        val createDto = CreateDistrictDto("District to Delete")
        val addedDistrict = service.addDistrict(createDto)

        val deleteResult = service.deleteDistrictById(addedDistrict.id)
        assertTrue(deleteResult)

        val exception = assertThrows<base.NotFoundException> {
            service.getDistrictById(addedDistrict.id)
        }
        assertEquals("Округ с ${addedDistrict.id} не найден", exception.message)
    }

    @Test
    fun `should return all districts`() {
        service.addDistrict(CreateDistrictDto("District"))
        service.addDistrict(CreateDistrictDto("DistrictSecond"))

        val allDistricts = service.getAllDistricts()
        assertEquals(2, allDistricts.size)
        assertTrue(allDistricts.any { it.name == "District" })
        assertTrue(allDistricts.any { it.name == "DistrictSecond" })
    }
}
