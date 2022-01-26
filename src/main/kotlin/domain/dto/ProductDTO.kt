package domain.dto

import domain.entities.Product
import org.jetbrains.exposed.sql.ResultRow
import java.math.BigDecimal

data class ProductDTO(
    val id: Int,
    val name: String,
    val priceBRL: BigDecimal,
    val otherCurrencies: MutableMap<String, BigDecimal>
) {

    companion object {

        fun convertResultRowToDTO(resultRow: ResultRow) = ProductDTO(
            id = resultRow[Product.id],
            name = resultRow[Product.name],
            priceBRL = resultRow[Product.priceBRL],
            otherCurrencies = 
        )

    }

}
