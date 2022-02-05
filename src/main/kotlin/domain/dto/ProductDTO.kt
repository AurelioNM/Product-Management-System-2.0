package domain.dto

import domain.entities.Currency
import domain.entities.Product
import org.jetbrains.exposed.sql.ResultRow
import storage.CurrencyRepository
import java.math.BigDecimal
import java.math.RoundingMode

data class ProductDTO(
    val name: String,
    val priceBRL: BigDecimal,
) {
    var otherCurrencies = mutableMapOf<String, BigDecimal>()
    var id: Int = 0

    override fun toString(): String {
        return "ProductDTO(" +
                "id=$id, " +
                "name=$name, " +
                "priceBRL=$priceBRL, " +
                "otherCurrencies=${otherCurrencies.toString()})"
    }

    companion object {
        fun convertResultRowToDTO(resultRow: ResultRow): ProductDTO {
            val productDTO = ProductDTO(
                name = resultRow[Product.name],
                priceBRL = resultRow[Product.priceBRL],
            )
            productDTO.otherCurrencies = insertingCurrencies(resultRow[Product.priceBRL])
            productDTO.id = resultRow[Product.id]
            return productDTO
        }

        private fun insertingCurrencies(priceBRL: BigDecimal): MutableMap<String, BigDecimal> {
            val jsonMap: Map<String, BigDecimal> = CurrencyRepository().getJsonMap()
            val map = mutableMapOf<String, BigDecimal>()
            jsonMap.forEach { map[it.key] = (it.value * priceBRL).setScale(2, RoundingMode.HALF_EVEN) }
            return map
        }
    }

}
