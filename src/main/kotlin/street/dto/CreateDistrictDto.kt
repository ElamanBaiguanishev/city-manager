package street.dto

import district.districtId

open class CreateStreetDto(val name: String, val districtId: districtId) {
    private val nameRegex = Regex("^[A-Za-zА-Яа-я0-9\\s-]+$") // Разрешает буквы, цифры, пробелы и тире
    private val uuidRegex = Regex("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$") // UUID

    init {
        validateName(name)
        validateDistrictId(districtId)
    }

    private fun validateName(name: String) {
        // Проверка на соответствие регулярному выражению
        if (!nameRegex.matches(name)) {
            throw IllegalArgumentException("Имя улицы может содержать только буквы, цифры, пробелы и тире.")
        }

        // Проверка на длину имени
        if (name.length > 40) {
            throw IllegalArgumentException("Имя улицы не может превышать 40 символов.")
        }
    }

    private fun validateDistrictId(districtId: districtId) {
        if (!uuidRegex.matches(districtId)) {
            throw IllegalArgumentException(
                "Некорректный ID округа: '$districtId'. ID должен быть в формате UUID."
            )
        }
    }
}
