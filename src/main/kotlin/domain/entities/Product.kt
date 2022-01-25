package domain.entities

import org.jetbrains.exposed.sql.CurrentDateTime
import org.jetbrains.exposed.sql.Table

object Product : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val name = varchar("name", 50)
    val priceBRL = decimal("priceBRL", 2, 2)
    val createdAt = CurrentDateTime()
    val updatedAt = datetime("createdAt")
}