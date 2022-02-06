package storage

import domain.entities.Currency
import java.math.BigDecimal


interface ICurrenciesRepository {
    fun getJsonMap(): Map<String, BigDecimal>
    fun getJsonStringFromUrl(): String
    fun convertJsonStringInMap(jsonString: String): Map<String, Currency>
    fun getMapFromRedis(): MutableMap<String, String>?
    fun insertJsonMapInRedis(jsonMapFromURL: Map<String, Currency>)
    fun clearRedis(expiration: Long)
}