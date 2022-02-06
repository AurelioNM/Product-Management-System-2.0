package service

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import domain.entities.Currency
import storage.RedisRepository
import java.lang.reflect.Type
import java.math.BigDecimal
import java.net.URL


class CurrenciesService: ICurrenciesService {

    private val redisRepository = RedisRepository()

    override fun getJsonStringFromUrl(): String = URL("https://economia.awesomeapi.com.br/all").readText()

    override fun convertJsonStringInMapAndInsertInRedis(jsonString: String): Map<String, Currency> {
        val mapType: Type = object : TypeToken<Map<String?, Currency?>?>() {}.type
        val jsonMap: Map<String, Currency> = Gson().fromJson(jsonString, mapType)

        redisRepository.setMap(jsonMap)

        return jsonMap
    }

    override fun getJsonMap(): Map<String, BigDecimal> {
        val mapFromRedis: MutableMap<String, String>? = redisRepository.getMap()
        val jsonMap = mutableMapOf<String, BigDecimal>()

        if (mapFromRedis?.isNotEmpty() == true) {
            mapFromRedis.forEach { jsonMap[it.key] = (it.value.toBigDecimal()) }
        } else {
            val mapFromURL: Map<String, Currency> = convertJsonStringInMapAndInsertInRedis(getJsonStringFromUrl())
            mapFromURL.forEach { jsonMap[it.key] = (it.value.ask) }
        }

        return jsonMap
    }

}
