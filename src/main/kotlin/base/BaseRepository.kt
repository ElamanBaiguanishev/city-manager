package base


/**CRUD репозиторий*/
interface BaseRepository<T, ID> {
    fun save(entity: T): T
    fun findById(id: ID): T?
    fun findAll(): List<T>
    fun update(entity: T): Boolean
    fun deleteById(id: ID): Boolean
}
