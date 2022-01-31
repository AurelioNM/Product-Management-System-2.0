package config

import org.jetbrains.exposed.sql.Database

interface IDbCon {
    fun connectDB(): Database
}
