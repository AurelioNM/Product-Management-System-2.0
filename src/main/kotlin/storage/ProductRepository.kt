package storage

import config.DbConnection
import config.IDbConnection
import domain.dto.ProductDTO
import domain.entities.Product
import io.javalin.http.NotFoundResponse
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.TransactionInterface
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses

class ProductRepository: IProductRepository {

    private val dbCon: IDbConnection = DbConnection()

    override fun getProducts(): List<ProductDTO> {
        return transaction(dbCon.connectDB()) {
            Product.selectAll().map { ProductDTO.convertProductRowsToDTO(it) }
        }
    }

    override fun getProductById(id: Int): ProductDTO? {
        return transaction(dbCon.connectDB()) {
            Product.select { Product.id eq id }.singleOrNull()?.let { ProductDTO.convertProductRowsToDTO(it) }
        }
    }

    override fun postProduct(productDTO: ProductDTO): ProductDTO {
        transaction(dbCon.connectDB()) {
            Product.insert {
                it[name] = productDTO.name
                it[priceBRL] = productDTO.priceBRL
            }
        }
        return productDTO
    }

    override fun updateProduct(id: Int, productDTO: ProductDTO) {
        transaction(dbCon.connectDB()) {

            Product.select { Product.id eq id }.singleOrNull() ?: throw NotFoundResponse()

            Product.update({ Product.id eq id }) {
                it[name] = productDTO.name
                it[priceBRL] = productDTO.priceBRL
            }

        }
    }

    override fun deleteProduct(id: Int) {
        transaction(dbCon.connectDB()) {
            Product.select { Product.id eq id }.singleOrNull() ?: throw NotFoundResponse()
            Product.deleteWhere { Product.id eq id }
        }
    }

}