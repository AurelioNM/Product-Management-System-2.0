import io.javalin.Javalin
import router.ProductRouter

fun main(args: Array<String>) {

    val app = Javalin.create().start(7000)

    val productRouter = ProductRouter()
    app.routes {
        productRouter.getProducts()
        productRouter.getProductById()
        productRouter.postProduct()
        productRouter.updateProduct()
        productRouter.deleteProduct()
    }

}