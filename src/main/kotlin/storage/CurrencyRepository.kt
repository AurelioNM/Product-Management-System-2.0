package storage

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import domain.entities.Currency
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import java.lang.reflect.Type
import java.net.URL
import java.util.*
import kotlin.concurrent.schedule


class CurrencyRepository: ICurrenciesRepository {

    override fun getJsonMap(): Map<String, Currency> {
        val jsonString: String = URL("https://economia.awesomeapi.com.br/all").readText()
        val mapType: Type = object : TypeToken<Map<String?, Currency?>?>() {}.type
        return Gson().fromJson(jsonString, mapType)
    }

    fun insertJsonMapInRedis() {
        val jsonMap: Map<String, Currency> = getJsonMap()

        val pool = JedisPool("localhost", 6379)
        val jedis = pool.resource
        jsonMap.forEach {
            jedis.sadd(it.key, it.value.ask.toString())
        }

        Timer().schedule(
            delay = 10000,
            action = { jedis.flushAll() }
        )

    }

}
