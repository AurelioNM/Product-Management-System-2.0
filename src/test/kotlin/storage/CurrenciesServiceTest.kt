package storage

import domain.entities.Currency
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import redis.clients.jedis.JedisPool
import service.CurrenciesService
import java.math.BigDecimal
import java.util.*
import kotlin.concurrent.schedule

internal class CurrenciesServiceTest {

    private val currenciesService = CurrenciesService()

    @Test
    fun `When getJsonStringFromURL() is called, string Should not be empty`() {
        val jsonString = currenciesService.getJsonStringFromUrl()
        assertFalse(jsonString.isEmpty())
    }

    @Test
    fun `Given json with 2 elements, When convertJsonStringInMap() is called, the map size Should be 2`() {
        val jsonString = "{\"USD\":{\"code\":\"USD\",\"codein\":\"BRL\",\"name\":\"Dólar Americano/Real Brasileiro\",\"high\":\"5.3502\",\"low\":\"5.2793\",\"varBid\":\"0.0445\",\"pctChange\":\"0.84\",\"bid\":\"5.3279\",\"ask\":\"5.3289\",\"timestamp\":\"1644010199\",\"create_date\":\"2022-02-04 18:29:59\"}," +
                "\"USDT\":{\"code\":\"USD\",\"codein\":\"BRLT\",\"name\":\"Dólar Americano/Real Brasileiro Turismo\",\"high\":\"5.48\",\"low\":\"5.41\",\"varBid\":\"0.035\",\"pctChange\":\"0.65\",\"bid\":\"5.3\",\"ask\":\"5.62\",\"timestamp\":\"1643993040\",\"create_date\":\"2022-02-04 13:44:00\"}}"
        val jsonMapFromURL = currenciesService.convertJsonStringInMap(jsonString)
        assertEquals(jsonMapFromURL.size, 2)
    }

    @Test
    fun `Given json with 2 elements, When convertJsonStringInMap() is called, the map Should contain the key CAD`() {
        val jsonString = "{\"USD\":{\"code\":\"USD\",\"codein\":\"BRL\",\"name\":\"Dólar Americano/Real Brasileiro\",\"high\":\"5.3502\",\"low\":\"5.2793\",\"varBid\":\"0.0445\",\"pctChange\":\"0.84\",\"bid\":\"5.3279\",\"ask\":\"5.3289\",\"timestamp\":\"1644010199\",\"create_date\":\"2022-02-04 18:29:59\"}," +
                "\"CAD\":{\"code\":\"USD\",\"codein\":\"BRLT\",\"name\":\"Dólar Americano/Real Brasileiro Turismo\",\"high\":\"5.48\",\"low\":\"5.41\",\"varBid\":\"0.035\",\"pctChange\":\"0.65\",\"bid\":\"5.3\",\"ask\":\"5.62\",\"timestamp\":\"1643993040\",\"create_date\":\"2022-02-04 13:44:00\"}}"
        val jsonMapFromURL = currenciesService.convertJsonStringInMap(jsonString)
        assertTrue(jsonMapFromURL.containsKey("CAD"))
    }


}