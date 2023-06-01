package repositories

import entities.Device
import org.springframework.jdbc.core.JdbcTemplate

class DeviceRepository(private val jdbcTemplate: JdbcTemplate) {

    fun insertDevice(device: Device) {
        jdbcTemplate.update("INSERT INTO user_devices (user_id, device_id) VALUES (?, ?)",
        device.userId,
        device.deviceId)
    }
}