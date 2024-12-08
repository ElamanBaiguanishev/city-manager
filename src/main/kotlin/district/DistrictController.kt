package district

import district.dto.CreateDistrictDto
import district.dto.UpdateDistrictDto

class DistrictController(
    private val service: DistrictService = DistrictService()
) {
    fun createDistrict(createDistrictDto: CreateDistrictDto): District {
        return service.addDistrict(createDistrictDto)
    }

    fun getDistrictById(id: districtId): District {
        return service.getDistrictById(id)
    }

    fun updateDistrict(updateDistrictDto: UpdateDistrictDto): District {
        return service.updateDistrict(updateDistrictDto)
    }

    fun deleteDistrictById(id: districtId): Boolean {
        return service.deleteDistrictById(id)
    }

    fun getAllDistricts(): List<District> {
        return service.getAllDistricts()
    }
}
