import base.BaseView
import district.DistrictView
import house.HouseView
import street.StreetView

class AppView(
    private val appController: AppController = AppController(),
    private val views: List<BaseView> = listOf(
        DistrictView(),
        StreetView(),
        HouseView()
    )
) {
    fun start() {
        println("Добро пожаловать в систему управления городом!")
        while (true) {
            println("\nВыберите действие:")
            println("1. Управление модулями (Районы, Улицы, Дома)")
            println("2. Общая информация по городу")
            println("0. Выход")

            when (readlnOrNull()?.toIntOrNull()) {
                1 -> startModules()
                2 -> showCityInfo()
                0 -> {
                    println("Выход из программы.")
                    return
                }

                else -> println("Неверный выбор, попробуйте снова.")
            }
        }
    }

    private fun startModules() {
        while (true) {
            println("\nВыберите модуль для работы:")
            views.forEachIndexed { index, view ->
                println("${index + 1}. ${view.moduleName}")
            }
            println("0. Назад")

            when (val choice = readlnOrNull()?.toIntOrNull()) {
                0 -> return
                in 1..views.size -> {
                    if (choice != null) {
                        println("Вы выбрали модуль: ${views[choice - 1].moduleName}")
                        views[choice - 1].start()
                    }
                }

                else -> println("Неверный выбор, попробуйте снова.")
            }
        }
    }

    private fun showCityInfo() {
        println("\nОбщая информация по городу:")
        val districtSatisfaction = appController.getAverageSatisfactionByDistrict()
        println("Средняя удовлетворенность по районам: $districtSatisfaction")

        val overallSatisfaction = appController.getOverallAverageSatisfaction()
        println("Общая средняя удовлетворенность: ${overallSatisfaction ?: "нет данных"}")

        val districtWithMostHouses = appController.getDistrictWithMostHouses()
        println("Район с наибольшим количеством домов: ${districtWithMostHouses ?: "нет данных"}")

        val importantInfo = appController.getImportantInfo()
        println(importantInfo)
    }
}
