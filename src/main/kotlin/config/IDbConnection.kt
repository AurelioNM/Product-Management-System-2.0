package config

import org.jetbrains.exposed.sql.Database

interface IDbConnection {
    fun connectDB(): Database
}
