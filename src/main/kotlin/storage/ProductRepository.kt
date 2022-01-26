package storage

import config.DbConnection
import domain.entities.Product
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class ProductRepository: IRepository {

    override fun getProducts(): List<ResultRow> {
        return transaction(DbConnection.connectDB()) {
            Product.selectAll().toList()
        }
    }

}