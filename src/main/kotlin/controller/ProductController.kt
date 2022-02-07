package controller

import domain.dto.ProductDTO
import io.javalin.http.Context
import org.eclipse.jetty.http.HttpStatus
import service.ProductService

class ProductController {
    private val service = ProductService()

    fun cacheOn(ctx: Context) {
        service.currenciesService.cacheOn()
    }

    fun cacheOff(ctx: Context) {
        service.currenciesService.cacheOff()
    }

    fun getProducts(ctx: Context) {
        val products = service.getProducts()
        ctx.json(products).status(HttpStatus.OK_200)
    }

    fun getProductsById(ctx: Context) {
        val id = ctx.pathParam("id")
        val product = service.getProductById(id.toInt())

        ctx.json(product).status(HttpStatus.OK_200)
    }

    fun postProduct(ctx: Context) {
        val product =
            ctx.bodyAsClass<ProductDTO>()


        val persistedProduct = service.postProduct(product)
        ctx.json(persistedProduct).status(HttpStatus.CREATED_201)
    }

    fun updateProduct(ctx: Context) {
        val id = ctx.pathParam("id")
        val product = try {
            ctx.bodyAsClass<ProductDTO>()
        } catch (e: Exception) {
            throw IllegalArgumentException()
        }
        service.updateProduct(id.toInt(), product)
        ctx.status(HttpStatus.OK_200)
    }

    fun deleteProduct(ctx: Context) {
        val id = ctx.pathParam("id")

        service.deleteProduct(id.toInt())
        ctx.status(HttpStatus.NO_CONTENT_204)
    }


}