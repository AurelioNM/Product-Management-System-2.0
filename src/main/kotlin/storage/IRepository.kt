package storage

import domain.dto.ProductDTO
import domain.entities.Product
import org.jetbrains.exposed.sql.ResultRow

interface IRepository {
    fun getProducts(): List<ProductDTO>
    fun getProductsById(id: Int): ProductDTO?
    fun postProduct(productDTO: ProductDTO): Boolean
    fun updateProduct(id: Int, productDTO: ProductDTO): Boolean
    fun deleteProduct(id: Int): Boolean
}