package district

import base.BaseView
import base.NotFoundException
import district.dto.CreateDistrictDto
import district.dto.UpdateDistrictDto

class DistrictView(
    private val controller: DistrictController = DistrictController()
) : BaseView {

    override val moduleName: String = "Управление округами"

    override fun start() {
        println("Добро пожаловать в систему управления округами!")
        while (true) {
            println("\nВыберите действие:")
            println("1. Добавить округ")
            println("2. Обновить округ")
            println("3. Удалить округ")
            println("4. Найти округ по ID")
            println("5. Показать все округа")
            println("0. Выход")

            when (readlnOrNull()?.toIntOrNull()) {
                1 -> addDistrict()
                2 -> updateDistrict()
                3 -> deleteDistrict()
                4 -> findDistrictById()
                5 -> listAllDistricts()
                0 -> {
                    println("Выход из программы.")
                    return
                }

                else -> println("Неверный ввод, попробуйте снова.")
            }
        }
    }

    private fun addDistrict() {
        println("Введите название округа:")
        val name = readlnOrNull().orEmpty()

        try {
            val dto = CreateDistrictDto(name)
            val district = controller.createDistrict(dto)
            println("Округ добавлен: $district")
        } catch (e: IllegalArgumentException) {
            println(e.message)
        }
    }

    private fun updateDistrict() {
        println("Введите ID округа для обновления:")
        val id = readlnOrNull().orEmpty()

        println("Введите новое название округа:")
        val name = readlnOrNull().orEmpty()

        try {
            val dto = UpdateDistrictDto(id, name)

            val success = controller.updateDistrict(dto)
            println("Округ успешно обновлен.")
            println(success)
        } catch (e: IllegalArgumentException) {
            println(e.message)
        } catch (e: NotFoundException) {
            println(e.message)
        }
    }

    private fun deleteDistrict() {
        println("Введите ID округа для удаления:")
        val id = readlnOrNull().orEmpty()

        val success = controller.deleteDistrictById(id)
        if (success) {
            println("Округ успешно удален.")
        } else {
            println("Округ с указанным ID не найден.")
        }
    }

    private fun findDistrictById() {
        println("Введите ID округа:")
        val id = readlnOrNull().orEmpty()
        try {
            val district = controller.getDistrictById(id)
            println("Найден округ: $district")
        } catch (e: NotFoundException) {
            println(e.message)
        }
    }

    private fun listAllDistricts() {
        val districts = controller.getAllDistricts()
        if (districts.isEmpty()) {
            println("Список округов пуст.")
        } else {
            println("Список всех округов:")
            districts.forEach { println(it) }
        }
    }
}
