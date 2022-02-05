package config

import org.jetbrains.exposed.sql.Database

class DbConnection: IDbConnection {

    override fun connectDB(): Database {
        return Database.connect(
            url = "jdbc:mysql://localhost:3306/pms2",
            driver = "com.mysql.cj.jdbc.Driver",
            user = "pms",
            password = "pms"
        )
    }

}