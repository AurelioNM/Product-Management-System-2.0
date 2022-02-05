package storage

import config.IDbConnection
import domain.dto.ProductDTO

interface IProductRepository {
    fun getProducts(dbCon: IDbConnection): List<ProductDTO>
    fun getProductsById(dbCon: IDbConnection, id: Int): ProductDTO?
    fun postProduct(dbCon: IDbConnection, productDTO: ProductDTO): ProductDTO
    fun updateProduct(dbCon: IDbConnection, id: Int, productDTO: ProductDTO)
    fun deleteProduct(dbCon: IDbConnection, id: Int)
}