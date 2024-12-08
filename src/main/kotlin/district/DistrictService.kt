package district

import base.NotFoundException
import district.dto.CreateDistrictDto
import district.dto.UpdateDistrictDto

class DistrictService(
    private val repository: DistrictRepository = DistrictRepository("src/main/resources/districts.json")
) {
    fun addDistrict(createDistrictDto: CreateDistrictDto): District {
        val district = District(
            name = createDistrictDto.name
        )
        repository.save(district)
        return district
    }

    fun getDistrictById(id: districtId): District {
        return repository.findById(id) ?: throw NotFoundException("Округ с $id не найден")
    }

    fun updateDistrict(updateDistrictDto: UpdateDistrictDto): District {
        val existingDistrict = getDistrictById(updateDistrictDto.id)
        val updatedDistrict = existingDistrict.copy(name = updateDistrictDto.name)
        return if (repository.update(updatedDistrict)) {
            updatedDistrict
        } else {
            throw Exception("Ошибка обновления")
        }
    }

    fun deleteDistrictById(id: districtId): Boolean {
        return repository.deleteById(id)
    }

    fun getAllDistricts(): List<District> {
        return repository.findAll()
    }
}
