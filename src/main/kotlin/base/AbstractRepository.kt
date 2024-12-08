package base

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.File
import java.util.*

@Serializable
data class ListWrapper<T>(val data: List<T>)


abstract class AbstractFileRepository<T : Model>(
    fileName: String,
    private val serializer: KSerializer<T>
) : BaseRepository<T, itemId> {
    private val file = File(fileName)

    init {
        if (!file.exists()) {
            file.createNewFile()
        }
    }

    private fun getStorage(): List<T> {
        return if (file.length() == 0L) {
            emptyList()
        } else {
            val listWrapperSerializer = ListWrapper.serializer(serializer)
            val jsonData = file.readText()
            val wrapper = Json.decodeFromString(listWrapperSerializer, jsonData)
            wrapper.data
        }
    }

    private fun saveAll(data: List<T>): Boolean {
        val wrapper = ListWrapper(data)
        val json = Json.encodeToString(ListWrapper.serializer(serializer), wrapper)
        file.writeText(json)  // Сохраняем в файл
        return true
    }

    override fun save(entity: T): T {
        val storage = getStorage()
        val updatedStorage = storage + entity
        saveAll(updatedStorage)
        return entity
    }

    override fun findById(id: itemId): T? {
        val storage = getStorage()
        return storage.find { it.id == id }
    }

    override fun findAll(): List<T> {
        return getStorage()
    }

    override fun update(entity: T): Boolean {
        val storage = getStorage()
        entity.updatedAt = Date()
        val updatedData = storage.map {
            if (it.id == entity.id) entity else it
        }
        saveAll(updatedData)
        return true
    }

    override fun deleteById(id: itemId): Boolean {
        val storage = getStorage()
        val updatedData = storage.filter { it.id != id }
        if (updatedData.size != storage.size) {
            saveAll(updatedData)
            return true
        }
        return false
    }
}
