package street

import district.districtId
import street.dto.CreateStreetDto
import street.dto.UpdateStreetDto

class StreetController(
    private val service: StreetService = StreetService()
) {
    fun createStreet(createStreetDto: CreateStreetDto): Street {
        return service.addStreet(createStreetDto)
    }

    fun getStreetById(id: streetId): Street {
        return service.getStreetById(id)
    }

    fun updateStreet(updateStreetDto: UpdateStreetDto): Street {
        return service.updateStreet(updateStreetDto)
    }

    fun deleteStreetById(id: streetId): Boolean {
        return service.deleteStreetById(id)
    }

    fun getAllStreets(): List<Street> {
        return service.getAllStreets()
    }

    fun getStreetsByDistrictId(districtId: districtId): List<Street> {
        return service.getStreetsByDistrictId(districtId)
    }
}
