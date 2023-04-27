package db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection
import java.sql.SQLException

object DataSource {
    private val config = HikariConfig()
    private var ds: HikariDataSource? = null

    init {
        config.jdbcUrl = "jdbc_url"
        config.username = "database_username"
        config.password = "database_password"
        config.addDataSourceProperty("cachePrepStmts", "true")
        config.addDataSourceProperty("prepStmtCacheSize", "250")
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
        ds = HikariDataSource(config)
    }

    @get:Throws(SQLException::class)
    val connection: Connection
        get() = ds!!.connection

    val dataSource = ds
}