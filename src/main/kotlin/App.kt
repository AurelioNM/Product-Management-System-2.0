import config.DbConnection
import domain.entities.Product
import io.javalin.Javalin
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import storage.CurrencyRepository

fun main(args: Array<String>) {

    val dbCon = DbConnection()

    transaction(dbCon.connectDB()) {

    }

    val app = Javalin.create().start(7000)
//    app.get("/") { ctx -> ctx.result() }

    CurrencyRepository().getJsonList()

}