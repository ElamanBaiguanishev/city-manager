package district.dto

class UpdateDistrictDto(val id: String, name: String) : CreateDistrictDto(name) {
    private val uuidRegex = Regex("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")

    init {
        validateId(id)
    }

    private fun validateId(id: String) {
        if (!uuidRegex.matches(id)) {
            throw IllegalArgumentException("Некорректный ID: '$id'. ID должен быть в формате UUID.")
        }
    }
}
