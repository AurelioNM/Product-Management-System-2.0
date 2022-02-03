package domain.dto

import domain.entities.Product
import org.jetbrains.exposed.sql.ResultRow
import storage.CurrencyRepository
import java.math.BigDecimal

data class ProductDTO(
    val name: String,
    val priceBRL: BigDecimal,
    val otherCurrencies: MutableMap<String, BigDecimal>
) {
    var id: Int = 0

    companion object {
        fun convertResultRowToDTO(resultRow: ResultRow): ProductDTO {
            val productDTO = ProductDTO(
                name = resultRow[Product.name],
                priceBRL = resultRow[Product.priceBRL],
                otherCurrencies = insertingCurrencies(resultRow[Product.priceBRL])
            )
            productDTO.id = resultRow[Product.id]
            return productDTO
        }

        private fun insertingCurrencies(priceBRL: BigDecimal): MutableMap<String, BigDecimal> {
            val jsonList = CurrencyRepository().getJsonMap()
            val map = mutableMapOf<String, BigDecimal>()
            jsonList.forEach { map[it.key] = (it.value.ask * priceBRL) }
            return map
        }
    }

}
