package base

import java.util.Date

typealias itemId = String

interface Model {
    val id: itemId
    val createdAt: Date
    var updatedAt: Date
}