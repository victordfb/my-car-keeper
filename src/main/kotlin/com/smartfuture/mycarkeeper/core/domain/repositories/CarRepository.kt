package com.smartfuture.mycarkeeper.core.domain.repositories

import com.smartfuture.mycarkeeper.core.domain.Car

interface CarRepository {
    fun getAllCars(): List<Car>
    fun findById(carId: String): Car?
    fun save(car: Car): Car
    fun delete(carId: String): Boolean
}
