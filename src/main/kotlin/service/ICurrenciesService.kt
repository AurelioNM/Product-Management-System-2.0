package service

import domain.entities.Currency
import java.math.BigDecimal


interface ICurrenciesService {
    fun getJsonMap(): Map<String, BigDecimal>
    fun getJsonStringFromUrl(): String
    fun convertJsonStringInMap(jsonString: String): Map<String, Currency>
}