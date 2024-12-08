package district

import base.AbstractFileRepository

class DistrictRepository(filePath: String) : AbstractFileRepository<District>(
    filePath,
    District.serializer()
) {

}