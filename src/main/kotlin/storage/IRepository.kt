package storage

import domain.dto.ProductDTO
import domain.entities.Product
import org.jetbrains.exposed.sql.ResultRow

interface IRepository {
    fun getProducts(): List<ProductDTO>
    fun getProductsById(id: Int): ProductDTO?
    fun postProduct(productDTO: ProductDTO): ProductDTO
    fun updateProduct(id: Int, productDTO: ProductDTO)
    fun deleteProduct(id: Int)
}