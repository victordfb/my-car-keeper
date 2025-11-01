package com.smartfuture.mycarkeeper.infra.inmemory

import com.smartfuture.mycarkeeper.core.domain.Car
import com.smartfuture.mycarkeeper.core.domain.repositories.CarRepository
import org.springframework.stereotype.Repository

@Repository
class InMemoryCarRepository : CarRepository {

    private val cars = mutableListOf(
        Car(id = "1", make = "Toyota", model = "Camry", year = 2023, vin = "1HGBH41JXMN109186"),
        Car(id = "2", make = "Honda", model = "Civic", year = 2022, vin = "2HGFC2F69KH551789"),
        Car(id = "3", make = "Ford", model = "Mustang", year = 2021, vin = "1FA6P8CFXM5147896")
    )

    override fun getAllCars(): List<Car> = cars.toList()

    override fun getCarDetails(carId: String): Car? = cars.firstOrNull { it.id == carId }
}
