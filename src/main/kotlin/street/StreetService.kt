package street

import base.NotFoundException
import district.districtId
import street.dto.CreateStreetDto
import street.dto.UpdateStreetDto

class StreetService(
    private val repository: StreetRepository = StreetRepository("src/main/resources/streets.json")
) {

    // Добавление новой улицы
    fun addStreet(createStreetDto: CreateStreetDto): Street {
        val street = Street(
            name = createStreetDto.name,
            districtId = createStreetDto.districtId
        )
        repository.save(street)
        return street
    }

    // Получение улицы по ID
    fun getStreetById(id: streetId): Street {
        return repository.findById(id) ?: throw NotFoundException("Улица с $id не найден")
    }

    // Обновление улицы
    fun updateStreet(updateStreetDto: UpdateStreetDto): Street {
        val existingStreet = getStreetById(updateStreetDto.id)
        val updatedStreet = existingStreet.copy(name = updateStreetDto.name, districtId = updateStreetDto.districtId)

        return if (repository.update(updatedStreet)) {
            updatedStreet
        } else {
            throw Exception("Ошибка обновления")
        }
    }

    // Удаление улицы по ID
    fun deleteStreetById(id: streetId): Boolean {
        return repository.deleteById(id)
    }

    // Получение всех улиц
    fun getAllStreets(): List<Street> {
        return repository.findAll()
    }

    // Получение всех улиц по ID округа
    fun getStreetsByDistrictId(districtId: districtId): List<Street> {
        return repository.findAll().filter { it.districtId == districtId }
    }
}

