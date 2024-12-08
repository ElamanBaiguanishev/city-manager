package street

import base.DateSerializer
import base.Model
import base.itemId
import district.districtId
import house.houseId
import kotlinx.serialization.Serializable
import java.util.*

typealias streetId = itemId

@Serializable
data class Street(
    override val id: streetId = UUID.randomUUID().toString(),
    val name: String,
    val districtId: districtId,
    val houses: MutableList<houseId> = mutableListOf(),
    @Serializable(with = DateSerializer::class)
    override val createdAt: Date = Date(),
    @Serializable(with = DateSerializer::class)
    override var updatedAt: Date = Date()
) : Model