package service

import config.DbConnection
import domain.dto.ProductDTO
import domain.entities.Product
import io.mockk.every
import io.mockk.mockk
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import storage.ProductRepository
import java.math.BigDecimal

internal class ProductServiceTest {

    private val productService = ProductService()
    private val repository = mockk<ProductRepository>(relaxed = true)

    @Test
    fun getProducts() {
        val productList = listOf<ProductDTO>(
            ProductDTO("primeiro", BigDecimal(1000)),
            ProductDTO("segundo", BigDecimal(1000)),
            ProductDTO("terceiro", BigDecimal(1000))
        )
        every { repository.getProducts() } returns productList
        assertEquals(3, productService.getProducts().size)
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