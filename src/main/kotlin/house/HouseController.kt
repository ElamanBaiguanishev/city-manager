package house

import house.dto.CreateHouseDto
import house.dto.UpdateHouseDto

class HouseController(
    private val service: HouseService = HouseService()
) {

    fun addHouse(createHouseDto: CreateHouseDto): House {
        return service.addHouse(createHouseDto)
    }

    fun updateHouse(updateHouseDto: UpdateHouseDto): House {
        return service.updateHouse(updateHouseDto)
    }

    fun deleteHouseById(id: houseId): Boolean {
        return service.deleteHouseById(id)
    }

    fun getHouseById(id: houseId): House {
        return service.getHouseById(id)
    }

    fun getAllHouses(): List<House> {
        return service.getAllHouses()
    }
}
