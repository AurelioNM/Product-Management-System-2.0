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

    private val jedis = JedisPool("localhost", 6379).resource

    override fun getJsonMapFromURL(): Map<String, Currency> {
        val jsonString: String = URL("https://economia.awesomeapi.com.br/all").readText()
        val mapType: Type = object : TypeToken<Map<String?, Currency?>?>() {}.type
        val jsonMap: Map<String, Currency> = Gson().fromJson(jsonString, mapType)
        insertJsonMapInRedis(jsonMap)
        return jsonMap
    }

    private fun insertJsonMapInRedis(jsonMapFromURL: Map<String, Currency>) {
        jsonMapFromURL.forEach {
            jedis.hset("currencies", it.key, it.value.ask.toString())
        }

        Timer().schedule(
            delay = 10000,
            action = { jedis.flushAll() }
        )
    }

    private fun gettingMapFromRedis(): MutableMap<String, String>? {
        return jedis.hgetAll("currencies")
    }

    fun getJsonMap(): Map<String, BigDecimal> {
        val mapFromRedis: MutableMap<String, String>? = gettingMapFromRedis()
        val jsonMap = mutableMapOf<String, BigDecimal>()

        if (mapFromRedis?.isNotEmpty() == true) {
            mapFromRedis.forEach { jsonMap[it.key] = (it.value.toBigDecimal()) }
        } else {
            val mapFromURL: Map<String, Currency> = getJsonMapFromURL()
            mapFromURL.forEach { jsonMap[it.key] = (it.value.ask) }
        }

        return jsonMap
    }

}
