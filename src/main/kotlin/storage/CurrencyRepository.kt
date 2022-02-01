package storage

import com.google.gson.Gson
import java.net.URL


class CurrencyRepository: ICurrenciesRepository {

//    @OptIn(ExperimentalStdlibApi::class)
    override fun getJsonList() {
    val jsonString: String = URL("https://economia.awesomeapi.com.br/all").readText()
    println(jsonString)
    val map = Gson().fromJson<Map<*, *>>(jsonString, MutableMap::class.java)
    println(map.toString())
}

}

//https://economia.awesomeapi.com.br/all/USD-BRL