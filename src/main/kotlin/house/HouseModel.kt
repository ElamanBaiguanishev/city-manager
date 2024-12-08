package house

import base.DateSerializer
import base.Model
import base.itemId
import kotlinx.serialization.*
import street.streetId
import java.util.*

typealias houseId = itemId

@Serializable
data class House(
    override val id: houseId = UUID.randomUUID().toString(),
    val streetId: streetId,
    val houseNumber: String,
    val satisfaction: Int? = null,
    @Serializable(with = DateSerializer::class)
    override val createdAt: Date = Date(),
    @Serializable(with = DateSerializer::class)
    override var updatedAt: Date = Date()
) : Model
