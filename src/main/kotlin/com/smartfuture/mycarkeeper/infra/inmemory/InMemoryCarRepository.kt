package com.smartfuture.mycarkeeper.infra.inmemory

import com.smartfuture.mycarkeeper.core.domain.Car
import com.smartfuture.mycarkeeper.core.domain.repositories.CarRepository
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository

@Repository
@Primary
@ConditionalOnProperty(
    name = ["app.repository.type"],
    havingValue = "inmemory"
)
@Suppress("unused")
class InMemoryCarRepository : CarRepository {

    private val cars = mutableListOf<Car>()

    override fun getAllCars(): List<Car> = cars.toList()

    override fun findById(carId: String): Car? = cars.firstOrNull { it.id == carId }

    override fun findByIdWithDetails(carId: String): Car? {
        return findById(carId)
    }

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
