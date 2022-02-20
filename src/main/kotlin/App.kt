import io.javalin.Javalin
import mu.KotlinLogging
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
        productRouter.enableCache()
        productRouter.disableCache()
    }

    val logger = KotlinLogging.logger {}

    logger.trace { "This is a trace log" }
    logger.debug { "This is a debug log" }
    logger.info { "This is a info log" }
    logger.warn { "This is a warn log" }
    logger.error { "This is a error log" }
}