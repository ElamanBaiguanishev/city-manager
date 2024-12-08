package street

import base.AbstractFileRepository

class StreetRepository(filePath: String) : AbstractFileRepository<Street>(
    filePath,
    Street.serializer()
) {

}