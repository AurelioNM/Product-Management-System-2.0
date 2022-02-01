package storage

import domain.entities.Currency


interface ICurrenciesRepository {
    fun getJsonList(): Map<String, Currency>
}