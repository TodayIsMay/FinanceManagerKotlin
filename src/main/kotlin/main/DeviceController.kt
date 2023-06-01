package main

import entities.Device
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class DeviceController(@Autowired managerBeans: ManagerBeans) {
    private val deviceService = managerBeans.deviceService()

    @PostMapping("devices/insert")
    fun insertDevice(@RequestBody device: Device) {
        deviceService.insertDevice(device)
    }
}