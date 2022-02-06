package storage

import domain.entities.Currency
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import redis.clients.jedis.JedisPool
import java.math.BigDecimal
import java.util.*
import kotlin.concurrent.schedule

internal class CurrencyRepositoryTest {

    private val currencyRepository = CurrencyRepository()
    private val jedis = JedisPool("localhost", 6379).resource

    @AfterEach
    fun afterAll() {
            val jedis = JedisPool("localhost", 6379).resource
            jedis.flushAll()
        }

    @Test
    fun `When getJsonStringFromURL() is called, string Should not be empty`() {
        val jsonString = currencyRepository.getJsonStringFromUrl()
        assertFalse(jsonString.isEmpty())
    }

    @Test
    fun `Given json with 2 elements, When convertJsonStringInMap() is called, the map size Should be 2`() {
        val jsonString = "{\"USD\":{\"code\":\"USD\",\"codein\":\"BRL\",\"name\":\"Dólar Americano/Real Brasileiro\",\"high\":\"5.3502\",\"low\":\"5.2793\",\"varBid\":\"0.0445\",\"pctChange\":\"0.84\",\"bid\":\"5.3279\",\"ask\":\"5.3289\",\"timestamp\":\"1644010199\",\"create_date\":\"2022-02-04 18:29:59\"}," +
                "\"USDT\":{\"code\":\"USD\",\"codein\":\"BRLT\",\"name\":\"Dólar Americano/Real Brasileiro Turismo\",\"high\":\"5.48\",\"low\":\"5.41\",\"varBid\":\"0.035\",\"pctChange\":\"0.65\",\"bid\":\"5.3\",\"ask\":\"5.62\",\"timestamp\":\"1643993040\",\"create_date\":\"2022-02-04 13:44:00\"}}"
        val jsonMapFromURL = currencyRepository.convertJsonStringInMap(jsonString)
        assertEquals(jsonMapFromURL.size, 2)
    }

    @Test
    fun `Given json with 2 elements, When convertJsonStringInMap() is called, the map Should contain the key CAD`() {
        val jsonString = "{\"USD\":{\"code\":\"USD\",\"codein\":\"BRL\",\"name\":\"Dólar Americano/Real Brasileiro\",\"high\":\"5.3502\",\"low\":\"5.2793\",\"varBid\":\"0.0445\",\"pctChange\":\"0.84\",\"bid\":\"5.3279\",\"ask\":\"5.3289\",\"timestamp\":\"1644010199\",\"create_date\":\"2022-02-04 18:29:59\"}," +
                "\"CAD\":{\"code\":\"USD\",\"codein\":\"BRLT\",\"name\":\"Dólar Americano/Real Brasileiro Turismo\",\"high\":\"5.48\",\"low\":\"5.41\",\"varBid\":\"0.035\",\"pctChange\":\"0.65\",\"bid\":\"5.3\",\"ask\":\"5.62\",\"timestamp\":\"1643993040\",\"create_date\":\"2022-02-04 13:44:00\"}}"
        val jsonMapFromURL = currencyRepository.convertJsonStringInMap(jsonString)
        assertTrue(jsonMapFromURL.containsKey("CAD"))
    }

    @Test
    fun `Given that 1 element is inserted in Redis, When getMapFromRedis() is called, it Should return something`() {
        jedis.hset("currencies", "DOGE", "2.2432")
        val mapFromRedis: MutableMap<String, String>? = currencyRepository.getMapFromRedis()
        assertEquals("2.2432", mapFromRedis?.get("DOGE"))
    }

    @Test
    fun `Given that 3 elements are inserted in Redis, When getMapFromRedis() is called, the map size Should be 3`() {
        jedis.hset("currencies", "DOGE", "2.2432")
        jedis.hset("currencies", "DOGE", "2.2432")
        jedis.hset("currencies", "DOGE", "2.2432")
        val mapFromRedis: MutableMap<String, String>? = currencyRepository.getMapFromRedis()
        assertEquals("2.2432", mapFromRedis?.get("DOGE"))
    }

    @Test
    fun `Given that a map with 2 elements is inserted in Redis, When insertJsonMapInRedis() is called, redis Should have 2 elements`() {
        val jsonMap = mutableMapOf<String, Currency>()
        jsonMap["DOGE"] = Currency(BigDecimal(0.777))
        jsonMap["CAD"] = Currency(BigDecimal(3.23))
        currencyRepository.insertJsonMapInRedis(jsonMap)

        val mapFromRedis = currencyRepository.getMapFromRedis()
        assertEquals(2, mapFromRedis?.size)
    }

    @Test
    fun getJsonMap() {
    }

    @Test
    fun `Given that a map with 2 elements is inserted in Redis, When clearRedis() is called, redis Should be empty`() {
        val jsonMap = mutableMapOf<String, Currency>()
        jsonMap["DOGE"] = Currency(BigDecimal(0.777))
        jsonMap["CAD"] = Currency(BigDecimal(3.23))
        currencyRepository.insertJsonMapInRedis(jsonMap)

        currencyRepository.clearRedis(0)
        val hget: String? = jedis.hget("currencies", "DOGE")
        Timer().schedule(
            delay = 1,
            action = { assertEquals(null, hget) }
        )

    }
}