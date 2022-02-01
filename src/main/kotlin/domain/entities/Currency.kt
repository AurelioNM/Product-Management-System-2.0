package domain.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.math.BigDecimal

@JsonClass(generateAdapter = true)
class Currency(
    @Json(name = "code") val code: String,
    @Json(name = "ask") val ask: BigDecimal
)