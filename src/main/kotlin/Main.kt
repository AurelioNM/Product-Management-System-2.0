import domain.Product
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun main(args: Array<String>) {

    val dbCon = Database.connect(
        url = "jdbc:mysql://localhost:3306/pms2DB",
        driver = "com.mysql.cj.jdbc.Driver",
        user = "pms",
        password = "pms"
    )

    transaction(dbCon) {
        SchemaUtils.create(Product)
    }

// jdbc:mysql://localhost:3306/pms2DB
// com.mysql.cj.jdbc.Driver
}