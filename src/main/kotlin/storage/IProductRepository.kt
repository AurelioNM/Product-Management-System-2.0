package storage

import domain.dto.ProductDTO

interface IProductRepository {
    fun getProducts(): List<ProductDTO>
    fun getProductById(id: Int): ProductDTO?
    fun postProduct(productDTO: ProductDTO): ProductDTO
    fun updateProduct(id: Int, productDTO: ProductDTO)
    fun deleteProduct(id: Int)
}