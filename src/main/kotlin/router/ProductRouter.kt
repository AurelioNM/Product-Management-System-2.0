package router

import controller.ProductController
import io.javalin.apibuilder.ApiBuilder
import io.javalin.http.Context
import org.eclipse.jetty.http.HttpStatus
import service.ProductService

class ProductRouter {

    private val controller = ProductController()
    private val service = ProductService()

    fun enableCache() {
        ApiBuilder.get(
            "/enablecache",
            controller::enableCache
        )
    }

    fun disableCache() {
        ApiBuilder.get(
            "/disablecache",
            controller::disableCache
        )
    }
///////////////
    fun getProducts() {
        ApiBuilder.get("/Product") { ctx ->
            val products = service.getProducts()
            ctx.json(products).status(HttpStatus.OK_200)
        }
    }

    fun getProductById() {
        ApiBuilder.get(
            "/Product/{id}",
            controller::getProductsById
        )
    }

    fun postProduct() {
        ApiBuilder.post(
            "/Product",
            controller::postProduct
        )
    }

    fun updateProduct() {
        ApiBuilder.put(
            "Product/{id}",
            controller::updateProduct
        )
    }

    fun deleteProduct() {
        ApiBuilder.delete(
            "/Product/{id}",
            controller::deleteProduct
        )
    }

}