package storage

import domain.entities.Currency

interface ICurrenciesRepository {
    fun getJsonList()
}