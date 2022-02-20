package router

import domain.dto.ProductDTO
import io.javalin.apibuilder.ApiBuilder
import org.eclipse.jetty.http.HttpStatus
import service.ProductService

class ProductRouter {

    private val service = ProductService()

    fun enableCache() {
        ApiBuilder.get("/enablecache") {
            service.currenciesService.configCache(true)
        }

    }

    fun disableCache() {
        ApiBuilder.get("/disablecache") {
            service.currenciesService.configCache(false)
        }
    }

    fun getProducts() {
        ApiBuilder.get("/Product") { ctx ->
            val products = service.getProducts()
            ctx.json(products).status(HttpStatus.OK_200)
        }
    }

    fun getProductById() {
        ApiBuilder.get("/Product/{id}") { ctx ->
            val id = ctx.pathParam("id")
            val product = service.getProductById(id.toInt())

            ctx.json(product).status(HttpStatus.OK_200)
        }
    }

    fun postProduct() {
        ApiBuilder.post("/Product") { ctx ->
            val product = ctx.bodyAsClass<ProductDTO>()

            val persistedProduct = service.postProduct(product)
            ctx.json(persistedProduct).status(HttpStatus.CREATED_201)
        }
    }

    fun updateProduct() {
        ApiBuilder.put("Product/{id}") { ctx ->
            val product = ctx.bodyAsClass<ProductDTO>()

            service.updateProduct(ctx.pathParam("id").toInt(), product)
            ctx.status(HttpStatus.OK_200)
        }
    }

    fun deleteProduct() {
        ApiBuilder.delete("/Product/{id}") { ctx ->
            val id = ctx.pathParam("id")

            service.deleteProduct(id.toInt())
            ctx.status(HttpStatus.NO_CONTENT_204)
        }
    }

}