import district.dto.CreateDistrictDto
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CreateDistrictDtoTest {

    @Test
    fun `should create DTO with valid name`() {
        val dto = CreateDistrictDto("Central District")
        assertEquals("Central District", dto.name)
    }

    @Test
    fun `should throw exception for invalid names`() {
        val invalidNames = listOf(
            "",                  // Пустое имя
            "central district",  // Имя с маленькой буквы
            "123District",       // Имя начинается с цифры
            "!@#District",       // Имя содержит специальные символы
            "Central1 District", // Имя содержит цифры
            " ",                 // Только пробелы
        )

        invalidNames.forEach { name ->
            assertThrows(IllegalArgumentException::class.java) {
                CreateDistrictDto(name)
            }
        }
    }

    @Test
    fun `should accept names with valid patterns`() {
        val validNames = listOf(
            "District",          // Одно слово
            "Central District",  // Несколько слов
            "New-District",      // Слово с дефисом
            "Дальний Восток",    // Имя на русском языке
            "Южный Регион"       // С пробелами и заглавной буквой
        )

        validNames.forEach { name ->
            val dto = CreateDistrictDto(name)
            assertEquals(name, dto.name)
        }
    }
}
