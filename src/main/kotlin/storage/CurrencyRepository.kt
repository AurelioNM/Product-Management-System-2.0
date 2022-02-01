package storage

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import domain.entities.Currency
import java.lang.reflect.Type
import java.net.URL


class CurrencyRepository: ICurrenciesRepository {

    override fun getJsonList(): Map<String, Currency> {
        val jsonString: String = URL("https://economia.awesomeapi.com.br/all").readText()
        val empMapType: Type = object : TypeToken<Map<String?, Currency?>?>() {}.type
        return Gson().fromJson(jsonString, empMapType)
    }

}
