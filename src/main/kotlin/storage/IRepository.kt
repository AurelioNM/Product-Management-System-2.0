package storage

import domain.entities.Product
import org.jetbrains.exposed.sql.ResultRow

interface IRepository {
    fun getProducts(): List<ResultRow>
//    fun getProductsById(id: Int): Product?
//    fun postProduct(product: Product)
//    fun saveProduct(id: Int, product: Product)
//    fun deleteProduct(id: Int)
}