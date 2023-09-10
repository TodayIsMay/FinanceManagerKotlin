package repositories

import entities.Principal
import org.springframework.jdbc.core.JdbcTemplate
import java.sql.ResultSet
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PrincipalRepository(private val jdbcTemplate: JdbcTemplate) {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun save(principal: Principal) {
        val sql = "INSERT INTO principals (username, password, salt) VALUES (?, ?, ?)"
        jdbcTemplate.update(sql, principal.username, principal.password, principal.salt)
    }

    fun findByUserName(username: String): List<Principal> {
        val principalList: MutableList<Principal> = arrayListOf()
        val sql = "SELECT * FROM principals WHERE username = '$username'"
        val set = jdbcTemplate.queryForList(sql)
        for (i in 0 until set.size) {
            val id = set[0]["id"].toString().toLong()
            val username = set[0]["username"].toString()
            val password = set[0]["password"].toString()
            val salt = if (set[0]["salt"] == null) null else set[0]["salt"].toString()
            val availableFunds = if (set[0]["available_funds"] == null) 0.0 else set[0]["available_funds"].toString().toDouble()
            val mbStartReportPeriod = set[0]["start_report_period"]
            val mbEndReportPeriod = set[0]["end_report_period"]
            val startReportPeriod = if (mbStartReportPeriod == null) null else LocalDate.parse(
                mbStartReportPeriod.toString(),
                formatter
            )
            val endReportPeriod =
                if (mbEndReportPeriod == null) null else LocalDate.parse(mbEndReportPeriod.toString(), formatter)
            principalList.add(
                Principal(
                    id,
                    username,
                    password,
                    salt,
                    availableFunds,
                    mutableSetOf(),
                    startReportPeriod,
                    endReportPeriod
                )
            )
        }
        return principalList
    }

    fun findById(id: Long): List<Principal> {
        val principalList: MutableList<Principal> = arrayListOf()
        val sql = "SELECT * FROM principals WHERE id = $id"
        val set = jdbcTemplate.queryForList(sql)
        for (i in 0 until set.size) {
            val id = set[0]["id"].toString().toLong()
            val username = set[0]["username"].toString()
            val password = set[0]["password"].toString()
            val salt = set[0]["salt"].toString()
            val availableFunds = set[0]["available_funds"].toString().toDouble()
            val mbStartReportPeriod = set[0]["start_report_period"]
            val mbEndReportPeriod = set[0]["end_report_period"]
            val startReportPeriod = if (mbStartReportPeriod == null) null else LocalDate.parse(
                mbStartReportPeriod.toString(),
                formatter
            )
            val endReportPeriod =
                if (mbEndReportPeriod == null) null else LocalDate.parse(mbEndReportPeriod.toString(), formatter)
            principalList.add(
                Principal(
                    id,
                    username,
                    password,
                    salt,
                    availableFunds,
                    mutableSetOf(),
                    startReportPeriod,
                    endReportPeriod
                )
            )
        }
        return principalList
    }

    fun setAvailableFundsToPrincipal(id: Long, availableFunds: Double): Principal {
        val principalList: MutableList<Principal> = arrayListOf()
        val updateSql = "UPDATE principals SET available_funds = ? WHERE id = ?"
        jdbcTemplate.update(updateSql, availableFunds, id)
        val querySql = "SELECT * FROM principals WHERE id = $id"

        val set = jdbcTemplate.queryForList(querySql)
        for (i in 0 until set.size) {
            val id = set[0]["id"].toString().toLong()
            val username = set[0]["username"].toString()
            val password = set[0]["password"].toString()
            val salt = set[0]["salt"].toString()
            val availableFunds = set[0]["available_funds"].toString().toDouble()
            val mbStartReportPeriod = set[0]["start_report_period"]
            val mbEndReportPeriod = set[0]["end_report_period"]
            val startReportPeriod = if (mbStartReportPeriod == null) null else LocalDate.parse(
                mbStartReportPeriod.toString(),
                formatter
            )
            val endReportPeriod =
                if (mbEndReportPeriod == null) null else LocalDate.parse(mbEndReportPeriod.toString(), formatter)
            principalList.add(
                Principal(
                    id,
                    username,
                    password,
                    salt,
                    availableFunds,
                    mutableSetOf(),
                    startReportPeriod,
                    endReportPeriod
                )
            )
        }
        return principalList[0]
    }

    fun setReportPeriodForPrincipal(id: Long, endReportPeriod: LocalDate) {
        val principalList: MutableList<Principal> = arrayListOf()
        val updateSql = "UPDATE principals SET end_report_period = ? WHERE id = ?"
        jdbcTemplate.update(updateSql, endReportPeriod, id)
    }
}