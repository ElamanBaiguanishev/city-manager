import district.DistrictService
import house.HouseService
import street.StreetService

class AppService(
    private val districtService: DistrictService = DistrictService(),
    private val streetService: StreetService = StreetService(),
    private val houseService: HouseService = HouseService()
) {
    // Средняя удовлетворенность по районам
    fun calculateAverageSatisfactionByDistrict(): Map<String, Double?> {
        val districts = districtService.getAllDistricts()
        return districts.associate { district ->
            val streets = streetService.getStreetsByDistrictId(district.id)
            val houses = streets.flatMap { street -> houseService.getHousesByStreetId(street.id) }
            val satisfactionValues = houses.mapNotNull { it.satisfaction }
            district.name to if (satisfactionValues.isNotEmpty()) satisfactionValues.average() else null
        }
    }

    // Общая средняя удовлетворенность
    fun calculateOverallAverageSatisfaction(): Double? {
        val allHouses = houseService.getAllHouses()
        val satisfactionValues = allHouses.mapNotNull { it.satisfaction }
        return if (satisfactionValues.isNotEmpty()) satisfactionValues.average() else null
    }

    // Район с наибольшим количеством домов
    fun getDistrictWithMostHouses(): String? {
        val districts = districtService.getAllDistricts()
        return districts.maxByOrNull { district ->
            val streets = streetService.getStreetsByDistrictId(district.id)
            streets.sumOf { street -> houseService.getHousesByStreetId(street.id).size }
        }?.name
    }

    // Важная информация
    fun getImportantInfo(): String {
        val totalDistricts = districtService.getAllDistricts().size
        val totalStreets = streetService.getAllStreets().size
        val totalHouses = houseService.getAllHouses().size
        return """
            Важная информация:
            - Всего районов: $totalDistricts
            - Всего улиц: $totalStreets
            - Всего домов: $totalHouses
        """.trimIndent()
    }
}
