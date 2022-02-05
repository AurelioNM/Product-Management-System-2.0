import config.DbConnection
import io.javalin.Javalin
import org.jetbrains.exposed.sql.transactions.transaction
import storage.CurrencyRepository
import storage.ProductRepository

fun main(args: Array<String>) {

    val dbCon = DbConnection()

    transaction(dbCon.connectDB()) {

    }

    val app = Javalin.create().start(7000)

}