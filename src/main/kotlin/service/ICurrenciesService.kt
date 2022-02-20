package service

import domain.entities.Currency
import java.math.BigDecimal


interface ICurrenciesService {
    fun configCache(cache: Boolean)
    fun getJsonMap(): Map<String, BigDecimal>
    fun getJsonStringFromUrl(): String
    fun convertJsonStringInMapAndInsertInRedis(jsonString: String): Map<String, Currency>
}