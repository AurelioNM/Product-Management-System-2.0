package storage

import config.DbConnection
import domain.dto.ProductDTO
import domain.entities.Product
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.math.BigDecimal

class ProductRepository: IRepository {

    override fun getProducts(): List<ProductDTO> = transaction(DbConnection.connectDB()) {
        Product.selectAll().map { ProductDTO.convertResultRowToDTO(it) }
    }

    override fun getProductsById(id: Int): ProductDTO? = transaction(DbConnection.connectDB()) {
        Product.select { Product.id eq id }.singleOrNull()?.let { ProductDTO.convertResultRowToDTO(it) }
    }

    override fun postProduct(productDTO: ProductDTO) {
        transaction(DbConnection.connectDB()) {
            Product.insert {
                it[name] = productDTO.name
                it[priceBRL] = productDTO.priceBRL.toString().toBigDecimal()
            }
        }
    }

    override fun updateProduct(id: Int, productDTO: ProductDTO) {
        transaction(DbConnection.connectDB()) {
            Product.update({ Product.id eq id }) {
                it[name] = productDTO.name
                it[priceBRL] = productDTO.priceBRL.toString().toBigDecimal()
            }
        }
    }

    override fun deleteProduct(id: Int) {
        transaction(DbConnection.connectDB()) {
            Product.deleteWhere { Product.id eq id }
        }
    }

}