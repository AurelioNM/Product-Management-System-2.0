package storage

import config.IDbConnection
import domain.dto.ProductDTO

interface IProductRepository {
    fun getProducts(): List<ProductDTO>
    fun getProductsById(id: Int): ProductDTO?
    fun postProduct(productDTO: ProductDTO): ProductDTO
    fun updateProduct(id: Int, productDTO: ProductDTO)
    fun deleteProduct(id: Int)
}