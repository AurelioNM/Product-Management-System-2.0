package storage

import config.DbConnection
import domain.dto.ProductDTO
import domain.entities.Product
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class ProductRepositoryTest {

    private val productRepository = ProductRepository()

    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            val defaultProducts = listOf(
                ProductDTO("Primeiro", BigDecimal(100)),
                ProductDTO("Segundo", BigDecimal(200)),
                ProductDTO("Terceiro", BigDecimal(300)),
                ProductDTO("Quarto", BigDecimal(200)),
                ProductDTO("Cinco", BigDecimal(200))
            )
            transaction(DbConnection().connectDB()) {
                SchemaUtils.createMissingTablesAndColumns(Product)
                defaultProducts.forEach { product ->
                    Product.insert {
                        it[name] = product.name
                        it[priceBRL] = product.priceBRL.toString().toBigDecimal()
                    }
                }
            }
        }

        @JvmStatic
        @AfterAll
        fun afterAll() {
            transaction(DbConnection().connectDB()) {
                SchemaUtils.drop(Product)
            }
        }
    }

    //GetProduct
    @Test
    fun `Given that BeeforeAll inserts values in DB, When getProducts() is called, attributes Should match`() {
        val products: List<ProductDTO> = productRepository.getProducts()
        val productDTO = ProductDTO("Primeiro", BigDecimal(100.00).setScale(2))
        assertEquals(productDTO.name, products[0].name)
    }

    //GetProductByID
    @Test
    fun `Given id = 2, When getProductsById() is called, attributes Should match`() {
        val product = productRepository.getProductsById(4)
        val productDTO = ProductDTO("Quarto", BigDecimal(400.00).setScale(2))
        assertEquals(productDTO.name, product?.name)
    }

    @Test
    fun `Given non existing id, When getProductsById() is called Should return null`() {
        val product = productRepository.getProductsById(98327)
        assertEquals(null, product)
    }

    //PostProduct
    @Test
    fun `When calling postProducts(), Should insert a new product`() {
        val productDTO = ProductDTO(
            name = "Novo Produto",
            priceBRL =  BigDecimal(99999.00).setScale(2)
        )
        productRepository.postProduct(productDTO)

        val lastId = productRepository.getProducts().last().id
        val lastProduct: ProductDTO? = productRepository.getProductsById(lastId)
        assertEquals(productDTO.name, lastProduct?.name)
        assertEquals(productDTO.priceBRL, lastProduct?.priceBRL)
    }

    //SaveProduct
    @Test
    fun `Given id = 3 When updateProduct() is called, Product should be updated`() {
        val productDTO = ProductDTO(
            name = "Produto Atualizado",
            priceBRL =  BigDecimal(77777.00).setScale(2)
        )
        productRepository.updateProduct(2, productDTO)
        val product = productRepository.getProductsById(2)?.name
        assertEquals("Produto Atualizado", product)
    }

    //DeleteProduct
    @Test
    fun `When deleteProduct() is called, Product should be deleted`() {
        productRepository.deleteProduct(5)
        val deletedProduct = productRepository.getProductsById(5)
        assertEquals(null, deletedProduct)
    }

}