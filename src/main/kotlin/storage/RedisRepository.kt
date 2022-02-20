package storage

import domain.entities.Currency
import io.github.cdimascio.dotenv.dotenv
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import java.util.*
import kotlin.concurrent.schedule

class RedisRepository: IRedisRepository {

    private val dotEnv = dotenv()
    private val twentyHours: Long = 72000000
    private val jedis: Jedis = JedisPool(dotEnv["REDIS_HOST"], dotEnv["REDIS_PORT"].toInt()).resource

    override fun getMap(): MutableMap<String, String>? {
        return jedis.hgetAll("currencies")
    }

    override fun setMap(jsonMapFromURL: Map<String, Currency>) {
        jsonMapFromURL.forEach {
            jedis.hset("currencies", it.key, it.value.ask.toString())
        }
        clearAll(twentyHours)
    }

    override fun clearAll(expiration: Long) {
        Timer().schedule(
            delay = expiration,
            action = { jedis.flushAll() }
        )
    }

    override fun flushAll() {
        jedis.flushAll()
    }

}