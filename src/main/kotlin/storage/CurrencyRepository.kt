package storage

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import domain.entities.Currency
import redis.clients.jedis.JedisPool
import java.lang.reflect.Type
import java.math.BigDecimal
import java.net.URL
import java.util.*
import kotlin.concurrent.schedule


class CurrencyRepository: ICurrenciesRepository {

    private val twoMinutes: Long = 120000
    private val jedis = JedisPool("localhost", 6379).resource

    override fun getJsonMap(): Map<String, BigDecimal> {
        val mapFromRedis: MutableMap<String, String>? = getMapFromRedis()
        val jsonMap = mutableMapOf<String, BigDecimal>()

        if (mapFromRedis?.isNotEmpty() == true) {
            mapFromRedis.forEach { jsonMap[it.key] = (it.value.toBigDecimal()) }
        } else {
            val mapFromURL: Map<String, Currency> = convertJsonStringInMap(getJsonStringFromUrl())
            mapFromURL.forEach { jsonMap[it.key] = (it.value.ask) }
        }

        return jsonMap
    }

    override fun convertJsonStringInMap(jsonString: String): Map<String, Currency> {
        val mapType: Type = object : TypeToken<Map<String?, Currency?>?>() {}.type
        val jsonMap: Map<String, Currency> = Gson().fromJson(jsonString, mapType)
        insertJsonMapInRedis(jsonMap)
        return jsonMap
    }

    override fun getJsonStringFromUrl(): String = URL("https://economia.awesomeapi.com.br/all").readText()

    override fun getMapFromRedis(): MutableMap<String, String>? {
        return jedis.hgetAll("currencies")
    }

    override fun insertJsonMapInRedis(jsonMapFromURL: Map<String, Currency>) {
        jsonMapFromURL.forEach {
            jedis.hset("currencies", it.key, it.value.ask.toString())
        }
        clearRedis(twoMinutes)
    }

    override fun clearRedis(expiration: Long) {
        Timer().schedule(
            delay = expiration,
            action = { jedis.flushAll() }
        )
    }

}
