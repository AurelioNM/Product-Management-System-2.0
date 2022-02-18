package router

import controller.ProductController
import io.javalin.apibuilder.ApiBuilder

class ProductRouter {

    private val controller = ProductController()

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

    fun getProducts() {
        ApiBuilder.get(
            "/Product",
            controller::getProducts
        )
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