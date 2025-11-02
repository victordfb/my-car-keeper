package com.smartfuture.mycarkeeper.application

import com.smartfuture.mycarkeeper.application.dto.CarDTO
import com.smartfuture.mycarkeeper.application.mappers.toDTO
import com.smartfuture.mycarkeeper.core.domain.repositories.CarRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@Suppress("unused")
@RestController
@Tag(name = "Cars", description = "Car management endpoints")
class CarController(
    private val carRepository: CarRepository,
) {

    @GetMapping("/cars")
    @Operation(summary = "List all cars", description = "Retrieves a list of all cars in the system")
    fun listCars(): ResponseEntity<List<CarDTO>> = ResponseEntity.ok(carRepository.getAllCars().map { it.toDTO() })

    @GetMapping("/cars/{id}/details")
    @Operation(
        summary = "Get car details",
        description = "Retrieves detailed information about a specific car by its ID"
    )
    fun carDetails(@PathVariable id: String): ResponseEntity<*> {
        val car = carRepository.findById(id)
        return if (car != null) {
            ResponseEntity.ok(car.toDTO())
        } else {
            ResponseEntity.status(404).body(mapOf("error" to "Car with id '$id' not found"))
        }
    }
}
