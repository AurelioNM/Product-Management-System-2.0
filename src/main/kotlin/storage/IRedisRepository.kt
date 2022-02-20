package storage

import domain.entities.Currency

interface IRedisRepository {
    fun getMap(): MutableMap<String, String>?
    fun setMap(jsonMapFromURL: Map<String, Currency>)
    fun clearAll(expiration: Long)
    fun flushAll()
}