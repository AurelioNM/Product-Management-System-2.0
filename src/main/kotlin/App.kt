import config.DbConnection
import domain.entities.Product
import io.javalin.Javalin
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun main(args: Array<String>) {

    val dbCon = DbConnection.connectDB()

    val teste1 = transaction(dbCon) {
        Product.selectAll().toList()
    }

    val app = Javalin.create().start(7000)




}