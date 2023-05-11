package db

import org.postgresql.ds.PGSimpleDataSource
import  javax.sql.DataSource

object DataSource {
    fun createDataSource(): DataSource {
        val url = "jdbc:postgresql://db:5432/db?user=postgres&password=postgres"
        val dataSource = PGSimpleDataSource()
        dataSource.setUrl(url)
        return dataSource
    }
}