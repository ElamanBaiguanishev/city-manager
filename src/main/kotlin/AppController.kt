class AppController(
    private val appService: AppService = AppService()
) {
    // Получение средней удовлетворенности по районам
    fun getAverageSatisfactionByDistrict(): Map<String, Double?> {
        return appService.calculateAverageSatisfactionByDistrict()
    }

    // Получение общей средней удовлетворенности
    fun getOverallAverageSatisfaction(): Double? {
        return appService.calculateOverallAverageSatisfaction()
    }

    // Получение района с наибольшим количеством домов
    fun getDistrictWithMostHouses(): String? {
        return appService.getDistrictWithMostHouses()
    }

    // Получение важной информации
    fun getImportantInfo(): String {
        return appService.getImportantInfo()
    }
}
