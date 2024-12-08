package district.dto

open class CreateDistrictDto(val name: String) {
    private val nameRegex = Regex("^[A-ZА-Я][a-zа-яA-ZА-Я\\s-]*$") // Разрешены слова, начинающиеся с заглавной буквы

    init {
        validateName(name) // Проверяем имя при создании объекта
    }

    private fun validateName(name: String) {
        if (!nameRegex.matches(name)) {
            throw IllegalArgumentException(
                "Некорректное имя: '$name'. Имя должно начинаться с заглавной буквы и содержать только буквы, пробелы и тире."
            )
        }
    }
}
