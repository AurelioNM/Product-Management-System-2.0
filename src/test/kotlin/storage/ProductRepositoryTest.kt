package storage

import org.jetbrains.exposed.sql.ResultRow
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ProductRepositoryTest {

    @Test
    fun `Should Return bla`() {
        val products: List<ResultRow> = ProductRepository().getProducts()
        assertFalse(products.isEmpty())
    }

    @Test
    fun `Should Return blo`() {
        val products: List<ResultRow> = ProductRepository().getProducts()
        val resultRow: ResultRow = products[3]
        assertEquals(1, products[3])
    }
}