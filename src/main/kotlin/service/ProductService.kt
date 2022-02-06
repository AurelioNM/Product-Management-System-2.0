package service

import domain.dto.ProductDTO
import io.javalin.http.NotFoundResponse
import storage.ProductRepository

class ProductService {

    private val repository = ProductRepository()

    fun getProducts(): List<ProductDTO> {
        val products: List<ProductDTO> = repository.getProducts()
        products.forEach { ProductDTO.populatingOtherCurrencies(it) }
        return products
    }
    fun getProductById(id: Int): ProductDTO {
        val productById: ProductDTO? = repository.getProductById(id)
        productById?.let {
            ProductDTO.populatingOtherCurrencies(it)
            return productById
        }
        throw NotFoundResponse()
    }
    fun postProduct(productDTO: ProductDTO): ProductDTO {
        return repository.postProduct(productDTO)
    }
    fun updateProduct(id: Int, productDTO: ProductDTO) {
        repository.updateProduct(id, productDTO)
    }
    fun deleteProduct(id: Int) {
        repository.deleteProduct(id)
    }
    
    

}