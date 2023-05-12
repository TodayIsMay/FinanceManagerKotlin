package main

import org.postgresql.ds.PGSimpleDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import services.ExpenseService

@Configuration
class ManagerBeans {

    @Bean
    fun createDataSource(): javax.sql.DataSource {
        val url = "jdbc:postgresql://db:5432/db?user=postgres&password=postgres"
        val dataSource = PGSimpleDataSource()
        dataSource.setUrl(url)
        return dataSource
    }

    @Bean
    fun jdbcTemplate(): JdbcTemplate {
        return JdbcTemplate(createDataSource())
    }
    @Bean
    fun expenseService(): ExpenseService {
        return ExpenseService(jdbcTemplate())
    }
}