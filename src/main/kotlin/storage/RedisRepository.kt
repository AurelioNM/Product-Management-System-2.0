package storage

import domain.entities.Currency
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import java.util.*
import kotlin.concurrent.schedule

class RedisRepository {

    private val twoMinutes: Long = 120000
    private val jedis: Jedis = JedisPool("localhost", 6379).resource

    fun getMap(): MutableMap<String, String>? {
        return jedis.hgetAll("currencies")
    }

    fun setMap(jsonMapFromURL: Map<String, Currency>) {
        jsonMapFromURL.forEach {
            jedis.hset("currencies", it.key, it.value.ask.toString())
        }
        clearAll(twoMinutes)
    }

    fun clearAll(expiration: Long) {
        Timer().schedule(
            delay = expiration,
            action = { jedis.flushAll() }
        )
    }

}