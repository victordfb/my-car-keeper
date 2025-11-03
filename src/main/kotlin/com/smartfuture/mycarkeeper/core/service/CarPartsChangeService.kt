package com.smartfuture.mycarkeeper.core.service

import com.smartfuture.mycarkeeper.core.domain.PartChange
import com.smartfuture.mycarkeeper.core.domain.repositories.CarRepository
import org.springframework.stereotype.Service
import java.time.Duration

@Suppress("unused")
@Service
class CarPartsChangeService(
    private val carRepository: CarRepository
) {

    fun setOdometerReading(carId: String, odometerReading: Long) {
        val car = carRepository.findById(carId)
        car?.setOdometerReading(odometerReading)
    }

    fun changePart(carId: String, partId: String, odometerExpiration: Long, duration: Duration) {
        val car = carRepository.findById(carId)
        car?.parts?.firstOrNull { it.id == partId }?.apply {
            car.setPartsChange(this, odometerExpiration, duration)
        }
    }

    fun getNextPartChanges(id: String): List<PartChange> {
        val car = carRepository.findById(id)
        return car?.getNextPartsChange() ?: listOf()
    }
}
