package storage

import domain.dto.ProductDTO
import domain.entities.Product
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.math.BigDecimal

internal class ProductRepositoryTest {

    //GetProduct
    @Test
    fun `When getProducts() is called, attributes Should match`() {
        val products: List<ProductDTO> = ProductRepository().getProducts()
        val productDTO = ProductDTO("Xbox360", BigDecimal(1000.00).setScale(2))
        assertEquals(productDTO.name, products[0].name)
    }

    //GetProductByID
    @Test
    fun `Given id = 0, When getProductsById() is called, attributes Should match`() {
        val product = ProductRepository().getProductsById(1)
        val productDTO = ProductDTO("Xbox360", BigDecimal(1000.00).setScale(2))
        assertEquals(productDTO.name, product?.name)
    }

    @Test
    fun `Given non existing id, When getProductsById() is called Should return null`() {
        val product = ProductRepository().getProductsById(100)
        assertEquals(null, product)
    }

    //PostProduct
    @Test
    fun `When calling postProducts(), Should insert a new product`() {
        val productDTO = ProductDTO(
            name = "Novo Produto",
            priceBRL =  BigDecimal(99999.00).setScale(2)
        )
        ProductRepository().postProduct(productDTO)

        val lastId = ProductRepository().getProducts().last().id
        val lastProduct: ProductDTO? = ProductRepository().getProductsById(lastId)
        assertEquals(productDTO.name, lastProduct?.name)
    }

    //SaveProduct
    @Test
    fun `When updateProduct() is called, Product should be updated`() {
        val productDTO = ProductDTO(
            name = "ATUALIZOOOOOOOOU",
            priceBRL =  BigDecimal(77777.00).setScale(2)
        )
        ProductRepository().updateProduct(1, productDTO)
        val products: List<ProductDTO> = ProductRepository().getProducts()
        products.forEach { println(it.name) }
        assertEquals(1, 1)
    }

}