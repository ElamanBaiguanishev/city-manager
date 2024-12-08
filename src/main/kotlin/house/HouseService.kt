package house

import base.NotFoundException
import house.dto.CreateHouseDto
import house.dto.UpdateHouseDto

class HouseService(
    private val repository: HouseRepository = HouseRepository("src/main/resources/houses.json")
) {

    // Добавление нового дома
    fun addHouse(createHouseDto: CreateHouseDto): House {
        val house = House(
            houseNumber = createHouseDto.houseNumber,
            streetId = createHouseDto.streetId,
            satisfaction = createHouseDto.satisfaction
        )
        repository.save(house)
        return house
    }

    // Обновление дома
    fun updateHouse(updateHouseDto: UpdateHouseDto): House {
        val existingHouse = getHouseById(updateHouseDto.id)
        val updatedHouse = existingHouse.copy(
            houseNumber = updateHouseDto.houseNumber,
            streetId = updateHouseDto.streetId,
            satisfaction = updateHouseDto.satisfaction
        )
        return if (repository.update(updatedHouse)) {
            updatedHouse
        } else {
            throw Exception("Ошибка обновления")
        }
    }

    // Удаление дома по ID
    fun deleteHouseById(id: houseId): Boolean {
        return repository.deleteById(id)
    }

    // Поиск дома по ID
    fun getHouseById(id: houseId): House {
        return repository.findById(id) ?: throw NotFoundException("Дом с $id не найден")
    }

    // Получение всех домов
    fun getAllHouses(): List<House> {
        return repository.findAll()
    }

    fun getHousesByStreetId(streetId: String): List<House> {
        return repository.findAll().filter { it.streetId == streetId }
    }
}
