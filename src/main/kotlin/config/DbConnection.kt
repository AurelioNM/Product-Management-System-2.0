package config

import io.github.cdimascio.dotenv.dotenv
import org.jetbrains.exposed.sql.Database

class DbConnection: IDbConnection {

    private val dotEnv = dotenv()

    override fun connectDB(): Database {
        return Database.connect(
            url = dotEnv["DATABASE_URL"],
            driver = dotEnv["DATABASE_DRIVER"],
            user = dotEnv["DATABASE_USER"],
            password = dotEnv["DATABASE_PASSWORD"]
        )
    }

}