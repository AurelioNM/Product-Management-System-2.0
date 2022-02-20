import io.javalin.Javalin
import io.javalin.plugin.openapi.OpenApiOptions
import io.javalin.plugin.openapi.OpenApiPlugin
import io.javalin.plugin.openapi.ui.ReDocOptions
import io.javalin.plugin.openapi.ui.SwaggerOptions
import io.swagger.v3.oas.models.info.Info


import router.ProductRouter

fun main(args: Array<String>) {

    val app = Javalin.create { config ->
        config.registerPlugin(getConfiguredOpenApiPlugin())
    }.start(7000)

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
}


fun getConfiguredOpenApiPlugin() = OpenApiPlugin(
    OpenApiOptions(
        Info().apply {
            version("1.0")
            description("App for product management")
        }
    ).apply {
        path("/swagger-docs") // endpoint for OpenAPI json
        swagger(SwaggerOptions("/swagger-ui")) // endpoint for swagger-ui
        reDoc(ReDocOptions("/redoc")) // endpoint for redoc
    }
)
