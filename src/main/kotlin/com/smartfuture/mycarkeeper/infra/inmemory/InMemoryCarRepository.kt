package com.smartfuture.mycarkeeper.infra.inmemory

import com.smartfuture.mycarkeeper.core.domain.Car
import com.smartfuture.mycarkeeper.core.domain.Part
import com.smartfuture.mycarkeeper.core.domain.repositories.CarRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
@Suppress("unused")
class InMemoryCarRepository : CarRepository {

    private val cars = mutableListOf(
        Car(id = "1", make = "Toyota", model = "Camry", year = 2023, vin = "1HGBH41JXMN109186").also {
            it.addPart(Part(UUID.randomUUID().toString(), "Motor Oil"))
            it.addPart(Part(UUID.randomUUID().toString(), "Air filter"))
            it.addPart(Part(UUID.randomUUID().toString(), "Brake pads"))
        },
        Car(id = "2", make = "Honda", model = "Civic", year = 2022, vin = "2HGFC2F69KH551789").also {
            it.addPart(Part(UUID.randomUUID().toString(), "Motor Oil"))
            it.addPart(Part(UUID.randomUUID().toString(), "Air filter"))
            it.addPart(Part(UUID.randomUUID().toString(), "Brake pads"))
        },
        Car(id = "3", make = "Ford", model = "Mustang", year = 2021, vin = "1FA6P8CFXM5147896").also {
            it.addPart(Part(UUID.randomUUID().toString(), "Motor Oil"))
            it.addPart(Part(UUID.randomUUID().toString(), "Air filter"))
            it.addPart(Part(UUID.randomUUID().toString(), "Brake pads"))
        }
    )

    override fun getAllCars(): List<Car> = cars.toList()

    override fun findById(carId: String): Car? = cars.firstOrNull { it.id == carId }
}
