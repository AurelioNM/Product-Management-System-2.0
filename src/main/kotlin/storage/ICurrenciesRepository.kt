package storage

import domain.entities.Currency
import java.math.BigDecimal


interface ICurrenciesRepository {
    fun getJsonMap(): Map<String, BigDecimal>
    fun getJsonMapFromURL(): Map<String, Currency>
    fun gettingMapFromRedis(): MutableMap<String, String>?
    fun insertJsonMapInRedis(jsonMapFromURL: Map<String, Currency>)
    fun clearRedis()
}