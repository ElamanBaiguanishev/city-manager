package street

import base.BaseView
import base.NotFoundException
import street.dto.CreateStreetDto
import street.dto.UpdateStreetDto

class StreetView(
    private val controller: StreetController = StreetController()
) : BaseView {

    override val moduleName: String = "Управление улицами"

    override fun start() {
        println("Добро пожаловать в систему управления улицами!")
        while (true) {
            println("\nВыберите действие:")
            println("1. Добавить улицу")
            println("2. Обновить улицу")
            println("3. Удалить улицу")
            println("4. Найти улицу по ID")
            println("5. Показать все улицы")
            println("6. Показать улицы в округе")
            println("0. Выход")

            when (readlnOrNull()?.toIntOrNull()) {
                1 -> addStreet()
                2 -> updateStreet()
                3 -> deleteStreet()
                4 -> findStreetById()
                5 -> listAllStreets()
                6 -> listStreetsByDistrict()
                0 -> {
                    println("Выход из программы.")
                    return
                }

                else -> println("Неверный ввод, попробуйте снова.")
            }
        }
    }

    private fun addStreet() {
        println("Введите название улицы:")
        val name = readlnOrNull().orEmpty()

        println("Введите ID округа:")
        val districtId = readlnOrNull().orEmpty()

        try {
            val dto = CreateStreetDto(name, districtId)
            val street = controller.createStreet(dto)
            println("Улица добавлена: $street")
        } catch (e: IllegalArgumentException) {
            println(e.message)
        }
    }

    private fun updateStreet() {
        println("Введите ID улицы для обновления:")
        val id = readlnOrNull().orEmpty()

        println("Введите новое название улицы:")
        val name = readlnOrNull().orEmpty()

        println("Введите новый ID округа:")
        val districtId = readlnOrNull().orEmpty()

        try {
            val dto = UpdateStreetDto(id, name, districtId)
            val success = controller.updateStreet(dto)
            println("Улица успешно обновлена.")
            println(success)
        } catch (e: IllegalArgumentException) {
            println(e.message)
        } catch (e: NotFoundException) {
            println(e.message)
        }
    }

    private fun deleteStreet() {
        println("Введите ID улицы для удаления:")
        val id = readlnOrNull().orEmpty()

        val success = controller.deleteStreetById(id)
        if (success) {
            println("Улица успешно удалена.")
        } else {
            println("Улица с указанным ID не найдена.")
        }
    }

    private fun findStreetById() {
        println("Введите ID улицы:")
        val id = readlnOrNull().orEmpty()
        try {
            val street = controller.getStreetById(id)
            println("Найдена улица: $street")
        } catch (e: NotFoundException) {
            println(e.message)
        }
    }

    private fun listAllStreets() {
        val streets = controller.getAllStreets()
        if (streets.isEmpty()) {
            println("Список улиц пуст.")
        } else {
            println("Список всех улиц:")
            streets.forEach { println(it) }
        }
    }

    private fun listStreetsByDistrict() {
        println("Введите ID округа:")
        val districtId = readlnOrNull().orEmpty()

        val streets = controller.getStreetsByDistrictId(districtId)
        if (streets.isEmpty()) {
            println("В данном округе нет улиц.")
        } else {
            println("Улицы в округе $districtId:")
            streets.forEach { println(it) }
        }
    }
}
