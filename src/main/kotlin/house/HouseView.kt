package house

import base.BaseView
import base.NotFoundException
import district.dto.CreateDistrictDto
import house.dto.CreateHouseDto
import house.dto.UpdateHouseDto

class HouseView(
    private val controller: HouseController = HouseController()
) : BaseView {

    override val moduleName: String = "Управление домами"

    override fun start() {
        println("Добро пожаловать в систему управления домами!")
        while (true) {
            println("\nВыберите действие:")
            println("1. Добавить дом")
            println("2. Обновить дом")
            println("3. Удалить дом")
            println("4. Найти дом по ID")
            println("5. Показать все дома")
//            println("6. Средняя удовлетворенность по району")
//            println("7. Общая средняя удовлетворенность")
            println("0. Выход")

            when (readlnOrNull()?.toIntOrNull()) {
                1 -> addHouse()
                2 -> updateHouse()
                3 -> deleteHouse()
                4 -> findHouseById()
                5 -> listAllHouses()
//                6 -> averageSatisfactionByDistrict()
//                7 -> overallAverageSatisfaction()
                0 -> {
                    println("Выход из программы.")
                    return
                }

                else -> println("Неверный ввод, попробуйте снова.")
            }
        }
    }

    private fun addHouse() {
        println("Введите ID улицы:")
        val streetId = readlnOrNull().orEmpty()

        println("Введите номер дома:")
        val houseNumber = readlnOrNull().orEmpty()

        println("Введите удовлетворенность (1-10 или оставьте пустым):")
        val satisfaction = readlnOrNull()?.toIntOrNull()

        try {
            val dto = CreateHouseDto(streetId, houseNumber, satisfaction)
            val house = controller.addHouse(dto)
            println("Дом добавлен: $house")
        } catch (e: IllegalArgumentException) {
            println(e.message)
        }
    }


    private fun updateHouse() {
        println("Введите ID дома для обновления:")
        val id = readlnOrNull().orEmpty()

        println("Введите новый ID улицы:")
        val streetId = readlnOrNull().orEmpty()

        println("Введите новый номер дома:")
        val houseNumber = readlnOrNull().orEmpty()

        println("Введите новую удовлетворенность (1-10 или оставьте пустым):")
        val satisfaction = readlnOrNull()?.toIntOrNull()

        try {
            val dto = UpdateHouseDto(id, streetId = streetId, houseNumber = houseNumber, satisfaction = satisfaction)

            val success = controller.updateHouse(dto)
            println("Дом успешно обновлен.")
            println(success)
        } catch (e: IllegalArgumentException) {
            println(e.message)
        } catch (e: NotFoundException) {
            println(e.message)
        }
    }


    private fun deleteHouse() {
        println("Введите ID дома для удаления:")
        val id = readlnOrNull().orEmpty()

        val success = controller.deleteHouseById(id)
        if (success) {
            println("Дом успешно удален.")
        } else {
            println("Дом с указанным ID не найден.")
        }
    }

    private fun findHouseById() {
        println("Введите ID дома:")
        val id = readlnOrNull().orEmpty()
        try {
            val house = controller.getHouseById(id)
            println("Найден дом: $house")
        } catch (e: NotFoundException) {
            println(e.message)
        }
    }

    private fun listAllHouses() {
        val houses = controller.getAllHouses()
        if (houses.isEmpty()) {
            println("Список домов пуст.")
        } else {
            println("Список всех домов:")
            houses.forEach { println(it) }
        }
    }
}
