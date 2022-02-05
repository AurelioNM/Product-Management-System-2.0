import config.DbConnection
import io.javalin.Javalin
import org.jetbrains.exposed.sql.transactions.transaction
import storage.CurrencyRepository

fun main(args: Array<String>) {

    val dbCon = DbConnection()

    transaction(dbCon.connectDB()) {

    }

    val app = Javalin.create().start(7000)

    val jsonMap = CurrencyRepository().insertJsonMapInRedis()

    CurrencyRepository().gettingMapFromRedis()

}