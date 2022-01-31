package domain.entities

import org.jetbrains.exposed.sql.CurrentDateTime
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime

object Product : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val name = varchar("name", 50)
    val priceBRL = decimal("priceBRL", 10, 2)
}