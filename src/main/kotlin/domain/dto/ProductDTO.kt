package domain.dto

import domain.entities.Product
import org.jetbrains.exposed.sql.ResultRow
import service.CurrenciesService
import java.math.BigDecimal
import java.math.RoundingMode

data class ProductDTO(
    val name: String,
    val priceBRL: BigDecimal,
) {
    var id: Int = 0
    var otherCurrencies = mutableMapOf<String, BigDecimal>()

    override fun toString(): String {
        return "ProductDTO(" +
                "id=$id, " +
                "name=$name, " +
                "priceBRL=$priceBRL, " +
                "otherCurrencies=${otherCurrencies})"
    }

    companion object {
        fun convertProductRowsToDTO(productResultRow: ResultRow): ProductDTO {
            val productDTO = ProductDTO(
                name = productResultRow[Product.name],
                priceBRL = productResultRow[Product.priceBRL],
            )
            productDTO.otherCurrencies = populatingOtherCurrencies(productResultRow[Product.priceBRL])
            productDTO.id = productResultRow[Product.id]
            return productDTO
        }

        private fun populatingOtherCurrencies(priceBRL: BigDecimal): MutableMap<String, BigDecimal> {
            val otherCurrenciesMap = mutableMapOf<String, BigDecimal>()
            CurrenciesService().getJsonMap().forEach {
                otherCurrenciesMap[it.key] = (it.value * priceBRL).setScale(2, RoundingMode.HALF_EVEN)
            }
            return otherCurrenciesMap
        }
    }
}
