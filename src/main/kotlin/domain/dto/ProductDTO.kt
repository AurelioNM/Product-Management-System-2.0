package domain.dto

import domain.entities.Product
import org.jetbrains.exposed.sql.ResultRow
import java.math.BigDecimal

class ProductDTO(
    val name: String,
    val priceBRL: BigDecimal
//    val otherCurrencies: MutableMap<String, BigDecimal>
) {
    var id: Int = 0

    companion object {

        fun convertResultRowToDTO(resultRow: ResultRow): ProductDTO {
            val productDTO = ProductDTO(
                name = resultRow[Product.name],
                priceBRL = resultRow[Product.priceBRL]
                //            otherCurrencies =
            )
            productDTO.id = resultRow[Product.id]
            return productDTO
        }

    }

}
