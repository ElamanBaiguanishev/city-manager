package district

import base.DateSerializer
import base.Model
import base.itemId
import kotlinx.serialization.Serializable
import java.util.*

typealias districtId = itemId

@Serializable
data class District(
    override val id: districtId = UUID.randomUUID().toString(),
    val name: String,
    val streets: MutableList<districtId> = mutableListOf(),
    @Serializable(with = DateSerializer::class)
    override val createdAt: Date = Date(),
    @Serializable(with = DateSerializer::class)
    override var updatedAt: Date = Date()
) : Model
