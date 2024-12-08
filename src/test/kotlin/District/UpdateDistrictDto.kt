import district.dto.UpdateDistrictDto
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class UpdateDistrictDtoTest {

    @Test
    fun `should create DTO with valid id and name`() {
        val id = "123e4567-e89b-12d3-a456-426614174000"
        val name = "Valid District"
        val dto = UpdateDistrictDto(id, name)

        assertEquals(id, dto.id)
        assertEquals(name, dto.name)
    }

    @Test
    fun `should throw exception for invalid UUID`() {
        val invalidIds = listOf(
            "",                         // Пустой ID
            "invalid-uuid",             // Несоответствующий формат
            "123e4567e89b12d3a456426614174000", // Без дефисов
            "123e4567-e89b-12d3-a456-42661417400G", // Неверный символ
            "123e4567-e89b-12d3-a456-"  // Неполный UUID
        )

        invalidIds.forEach { id ->
            assertThrows(IllegalArgumentException::class.java) {
                UpdateDistrictDto(id, "Valid Name")
            }
        }
    }

    @Test
    fun `should throw exception for invalid names`() {
        val validId = "123e4567-e89b-12d3-a456-426614174000"
        val invalidNames = listOf(
            "",                  // Пустое имя
            "invalid name",      // Начинается с маленькой буквы
            "123District",       // Содержит цифры
            "!@#District",       // Специальные символы
            "District#",         // Неверный символ в конце
            "District 1"         // Наличие цифры
        )

        invalidNames.forEach { name ->
            assertThrows(IllegalArgumentException::class.java) {
                UpdateDistrictDto(validId, name)
            }
        }
    }

    @Test
    fun `should accept valid id and name`() {
        val validIds = listOf(
            "123e4567-e89b-12d3-a456-426614174000",
            "3fa85f64-5717-4562-b3fc-2c963f66afa6",
            "550e8400-e29b-41d4-a716-446655440000"
        )

        val validNames = listOf(
            "Valid Name",
            "Valid-Name",
            "Название на русском",
            "New District",
            "Another-Valid District"
        )

        validIds.forEach { id ->
            validNames.forEach { name ->
                val dto = UpdateDistrictDto(id, name)
                assertEquals(id, dto.id)
                assertEquals(name, dto.name)
            }
        }
    }
}
