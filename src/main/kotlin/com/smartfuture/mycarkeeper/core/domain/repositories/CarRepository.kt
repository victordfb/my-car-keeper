package com.smartfuture.mycarkeeper.core.domain.repositories

import com.smartfuture.mycarkeeper.core.domain.Car

interface CarRepository {

    fun getAllCars(): List<Car>
    fun getCarDetails(carId: String): Car?
}
