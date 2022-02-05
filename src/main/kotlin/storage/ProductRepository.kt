package storage

import config.IDbConnection
import domain.dto.ProductDTO
import domain.entities.Product
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class ProductRepository: IProductRepository {

    override fun getProducts(dbCon: IDbConnection): List<ProductDTO> {
        return transaction(dbCon.connectDB()) {
            Product.selectAll().map { ProductDTO.convertProductRowsToDTO(it) }
        }
    }

    override fun getProductsById(dbCon: IDbConnection, id: Int): ProductDTO? {
        return transaction(dbCon.connectDB()) {
            Product.select { Product.id eq id }.singleOrNull()?.let { ProductDTO.convertProductRowsToDTO(it) }
        }
    }

    override fun postProduct(dbCon: IDbConnection, productDTO: ProductDTO): ProductDTO {
        transaction(dbCon.connectDB()) {
            Product.insert {
                it[name] = productDTO.name
                it[priceBRL] = productDTO.priceBRL
            }
        }
        return productDTO
    }

    override fun updateProduct(dbCon: IDbConnection, id: Int, productDTO: ProductDTO) {
        transaction(dbCon.connectDB()) {
            Product.update({ Product.id eq id }) {
                it[name] = productDTO.name
                it[priceBRL] = productDTO.priceBRL
            }
        }
    }

    override fun deleteProduct(dbCon: IDbConnection, id: Int) {
        transaction(dbCon.connectDB()) {
            Product.deleteWhere { Product.id eq id }
        }
    }

}