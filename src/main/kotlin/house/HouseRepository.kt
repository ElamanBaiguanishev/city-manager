package house

import base.AbstractFileRepository

/** Реализация репозитория для домов */
class HouseRepository(filePath: String) : AbstractFileRepository<House>(
    filePath,
    House.serializer()
) {

}