package storage

import com.google.protobuf.Message
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapter
import domain.entities.Currency
import okhttp3.OkHttpClient
import org.eclipse.jetty.client.api.Request
import java.io.IOException
import java.lang.reflect.Type
import java.net.URL

class CurrencyRepository: ICurrenciesRepository {

    private val client = OkHttpClient()
    private val moshi = Moshi.Builder().build()
    private val gistJsonAdapter = moshi.adapter(Gist::class.java)

//    @OptIn(ExperimentalStdlibApi::class)
    override fun getJsonList() {
        val request = Request.Builder()
            .url("https://api.github.com/gists/c2a7c39532239ff261be")
            .build()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val gist = gistJsonAdapter.fromJson(response.body!!.source())

            for ((key, value) in gist!!.files!!) {
                println(key)
                println(value.content)
            }
        }
    }

}