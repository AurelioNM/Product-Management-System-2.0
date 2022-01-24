import io.javalin.Javalin
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>) {

    val app = Javalin.create().start(7000)
    app.get("/") { ctx -> ctx.result("Hello World") }

    val dbCon = Database.connect(
        url = "jdbc:mysql://localhost:3306/pms2",
        driver = "com.mysql.cj.jdbc.Driver",
        user = "pms",
        password = "pms"
    )

//    transaction(dbCon) {
//        SchemaUtils.createMissingTablesAndColumns(Product)
//    }


}