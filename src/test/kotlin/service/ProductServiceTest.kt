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