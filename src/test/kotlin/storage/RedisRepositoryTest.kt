package storage

import domain.entities.Currency
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import java.math.BigDecimal
import java.util.*
import kotlin.concurrent.schedule

internal class RedisRepositoryTest {

    private val redisRepository = RedisRepository()
    private val jedis: Jedis = JedisPool("localhost", 6379).resource

    @AfterEach
    fun afterEach() {
        jedis.flushAll()
    }

    @Test
    fun `Given that 1 element is inserted in Redis, When getMap() is called, it Should return a map with the same values`() {
        jedis.hset("currencies", "DOGE", "2.2432")
        val mapFromRedis: MutableMap<String, String>? = redisRepository.getMap()
        assertEquals("2.2432", mapFromRedis?.get("DOGE"))
    }

    @Test
    fun `Given that 3 elements are inserted in Redis, When getMap() is called, the map size Should be 3`() {
        jedis.hset("currencies", "DOGE", "2.2432")
        jedis.hset("currencies", "DOGE", "2.2432")
        jedis.hset("currencies", "DOGE", "2.2432")
        val mapFromRedis: MutableMap<String, String>? = redisRepository.getMap()
        assertEquals("2.2432", mapFromRedis?.get("DOGE"))
    }

    @Test
    fun `Given that a map with 2 elements is inserted in Redis, When setMap() is called, redis Should have 2 elements`() {
        val jsonMap = mutableMapOf<String, Currency>()
        jsonMap["DOGE"] = Currency(BigDecimal(0.777))
        jsonMap["CAD"] = Currency(BigDecimal(3.23))
        redisRepository.setMap(jsonMap)

        val mapFromRedis = redisRepository.getMap()
        assertEquals(2, mapFromRedis?.size)
    }

    @Test
    fun `Given that a map with 2 elements is inserted in Redis, When clearAll() is called, redis Should return null`() {
        val jsonMap = mutableMapOf<String, Currency>()
        jsonMap["DOGE"] = Currency(BigDecimal(0.777))
        jsonMap["CAD"] = Currency(BigDecimal(3.23))
        redisRepository.setMap(jsonMap)

        redisRepository.clearAll(0)
        val currencyIntem: String? = jedis.hget("currencies", "DOGE")

        Timer().schedule(
            delay = 1,
            action = { assertEquals(null, currencyIntem) }
        )
    }

}