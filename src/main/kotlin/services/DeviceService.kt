package services

import entities.Device
import org.springframework.jdbc.core.JdbcTemplate
import repositories.DeviceRepository

class DeviceService(jdbcTemplate: JdbcTemplate) {
    private val deviceRepository = DeviceRepository(jdbcTemplate)
    fun insertDevice(device: Device) {
        deviceRepository.insertDevice(device)
    }
}