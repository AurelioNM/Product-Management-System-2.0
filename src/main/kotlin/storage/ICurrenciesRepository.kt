package storage

import domain.entities.Currency


interface ICurrenciesRepository {
    fun getJsonMap(): Map<String, Currency>
}