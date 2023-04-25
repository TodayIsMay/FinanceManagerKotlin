package db

import java.sql.Connection
import java.sql.DriverManager

class DBConnector {
    private val url: String = "jdbc:postgresql://localhost:5432/db"
    private val user: String = "postgres"
    private val password: String = "postgres"

    fun connect(): Connection? {
        var connection: Connection? = null
        try {
            connection = DriverManager.getConnection(url, user, password)
        } catch (e: Exception) {
            println(e.message)
        }
        return connection
    }
}