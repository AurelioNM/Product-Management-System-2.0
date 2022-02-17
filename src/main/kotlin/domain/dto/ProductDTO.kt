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
            val product = ProductDTO(
                name = productResultRow[Product.name],
                priceBRL = productResultRow[Product.priceBRL],
            )
            product.id = productResultRow[Product.id]
            return product
        }

        fun populatingOtherCurrencies(currenciesService: CurrenciesService, productDTO: ProductDTO) {
            currenciesService.getJsonMap().forEach {
                productDTO.otherCurrencies[it.key] = (it.value * productDTO.priceBRL).setScale(2, RoundingMode.HALF_EVEN)
            }
        }
    }
}
