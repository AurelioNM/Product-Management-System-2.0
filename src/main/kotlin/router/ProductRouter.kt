package router

import controller.ProductController
import io.javalin.apibuilder.ApiBuilder

class ProductRouter {

    private val productController = ProductController()

    fun getProducts() {
        ApiBuilder.get(
            "/Product",
            productController::getProducts
        )
    }

    fun getProductById() {
        ApiBuilder.get(
            "/Product/{id}",
            productController::getProductsById
        )
    }

    fun postProduct() {
        ApiBuilder.post(
            "/Product",
            productController::postProduct
        )
    }

    fun updateProduct() {
        ApiBuilder.put(
            "Product/{id}",
            productController::updateProduct
        )
    }

    fun deleteProduct() {
        ApiBuilder.delete(
            "/Product/{id}",
            productController::deleteProduct
        )
    }

}