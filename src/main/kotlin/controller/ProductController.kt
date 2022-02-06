package controller

import domain.dto.ProductDTO
import io.javalin.http.Context
import org.eclipse.jetty.http.HttpStatus
import service.ProductService

class ProductController {

    private val productService = ProductService()

    fun getProducts(ctx: Context) {
        val products = productService.getProducts()
        ctx.json(products).status(HttpStatus.OK_200)
    }

    fun getProductsById(ctx: Context) {
        val id = ctx.pathParam("id")
        val product = productService.getProductById(id.toInt())

        ctx.json(product).status(HttpStatus.OK_200)
    }

    fun postProduct(ctx: Context) {
        val product = try {
            ctx.bodyAsClass<ProductDTO>()
        } catch (e: Exception) {
            throw IllegalArgumentException()
        }

        val persistedProduct = productService.postProduct(product)
        ctx.json(persistedProduct).status(HttpStatus.CREATED_201)
    }

    fun updateProduct(ctx: Context) {
        val id = ctx.pathParam("id")
        val product = try {
            ctx.bodyAsClass<ProductDTO>()
        } catch (e: Exception) {
            throw IllegalArgumentException()
        }
        productService.updateProduct(id.toInt(), product)
        ctx.status(HttpStatus.NO_CONTENT_204)
    }

    fun deleteProduct(ctx: Context) {
        val id = ctx.pathParam("id")

        productService.deleteProduct(id.toInt())
        ctx.status(HttpStatus.NO_CONTENT_204)
    }


}