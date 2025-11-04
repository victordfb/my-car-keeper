package com.smartfuture.mycarkeeper.infra.inmemory

import com.smartfuture.mycarkeeper.core.domain.Car
import com.smartfuture.mycarkeeper.core.domain.repositories.CarRepository
import org.springframework.stereotype.Repository

@Repository
@Suppress("unused")
class InMemoryCarRepository : CarRepository {

    private val cars = mutableListOf<Car>()

    override fun getAllCars(): List<Car> = cars.toList()

    override fun findById(carId: String): Car? = cars.firstOrNull { it.id == carId }

    override fun save(car: Car): Car {
        val existingIndex = cars.indexOfFirst { it.id == car.id }
        if (existingIndex != -1) {
            cars[existingIndex] = car
        } else {
            cars.add(car)
        }
        return car
    }

    override fun delete(carId: String): Boolean = cars.removeIf { it.id == carId }
}
