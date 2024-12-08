package house.dto

import street.streetId

open class CreateHouseDto(val streetId: streetId, val houseNumber: String, val satisfaction: Int?) {
    private val uuidRegex = Regex("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")
    private val houseNumberRegex = Regex("^[A-Za-z0-9\\-\\s]+$")

    init {
        validateStreetId(streetId)
        validateSatisfaction(satisfaction)
        validateHouseNumber(houseNumber)
    }

    private fun validateStreetId(streetId: streetId) {
        require(uuidRegex.matches(streetId)) {
            "Некорректный streetId: '$streetId'. ID должен быть в формате UUID."
        }
    }

    private fun validateSatisfaction(satisfaction: Int?) {
        require(satisfaction == null || (satisfaction in 1..10)) {
            "Некорректное значение satisfaction: $satisfaction. Допустимые значения: от 1 до 10 или null."
        }
    }

    private fun validateHouseNumber(houseNumber: String) {
        require(houseNumber.length <= 10) {
            "Номер дома '$houseNumber' не может превышать 10 символов."
        }
        require(houseNumberRegex.matches(houseNumber)) {
            "Номер дома '$houseNumber' должен содержать только буквы, цифры, тире и пробелы."
        }
    }
}
