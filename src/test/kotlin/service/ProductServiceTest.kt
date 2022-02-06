package service

import config.DbConnection
import domain.dto.ProductDTO
import domain.entities.Product
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import java.math.BigDecimal

internal class ProductServiceTest {

    private val productService = ProductService()

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

    @Test
    fun getProducts() {
        val products = productService.getProducts()
        products.forEach { println(it.toString()) }
    }

    @Test
    fun getProductById() {
    }

    @Test
    fun postProduct() {
    }

    @Test
    fun updateProduct() {
    }

    @Test
    fun deleteProduct() {
    }
}