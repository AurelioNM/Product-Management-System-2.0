package domain.dto

import domain.entities.Product
import org.jetbrains.exposed.sql.ResultRow
import service.CurrenciesService
import java.math.BigDecimal
import java.math.RoundingMode

data class ProductDTO(
    val id: Int,
    val name: String,
    val priceBRL: BigDecimal,
) {
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
            return ProductDTO(
                id = productResultRow[Product.id],
                name = productResultRow[Product.name],
                priceBRL = productResultRow[Product.priceBRL],
            )
        }

        fun populatingOtherCurrencies(currenciesService: CurrenciesService, productDTO: ProductDTO) {
            currenciesService.getJsonMap().forEach {
                productDTO.otherCurrencies[it.key] = (it.value * productDTO.priceBRL).setScale(2, RoundingMode.HALF_EVEN)
            }
        }
    }
}
