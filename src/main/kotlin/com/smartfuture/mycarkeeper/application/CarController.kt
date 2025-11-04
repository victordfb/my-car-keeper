package com.smartfuture.mycarkeeper.application

import com.smartfuture.mycarkeeper.application.dto.CarDTO
import com.smartfuture.mycarkeeper.application.mappers.toDTO
import com.smartfuture.mycarkeeper.application.mappers.toDto
import com.smartfuture.mycarkeeper.core.domain.Car
import com.smartfuture.mycarkeeper.core.domain.Part
import com.smartfuture.mycarkeeper.core.domain.repositories.CarRepository
import com.smartfuture.mycarkeeper.core.service.CarPartsChangeService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.time.Duration
import java.util.*

@Suppress("unused")
@RestController
@RequestMapping("/car")
@Tag(name = "Cars", description = "Car management endpoints")
class CarController(
    private val carRepository: CarRepository,
    private val carPartsChangeService: CarPartsChangeService,
) {

    @GetMapping
    @Operation(summary = "List all cars", description = "Retrieves a list of all cars in the system")
    fun listCars(): ResponseEntity<List<CarDTO>> = ResponseEntity.ok(carRepository.getAllCars().map { it.toDTO() })

    @GetMapping("/{id}/details")
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

    @PostMapping
    @Operation(
        summary = "Create a new car",
        description = "Creates a new car in the system"
    )
    fun createCar(@RequestBody request: CreateUpdateCarRequest): ResponseEntity<*> {
        val car = Car(
            id = UUID.randomUUID().toString(),
            make = request.make,
            model = request.model,
            year = request.year,
            vin = request.vin
        )
        val savedCar = carRepository.save(car)
        return ResponseEntity.created(URI.create("/car/${savedCar.id}/details")).body(savedCar.toDTO())
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Update an existing car",
        description = "Updates an existing car by its ID"
    )
    fun updateCar(@PathVariable id: String, @RequestBody request: CreateUpdateCarRequest): ResponseEntity<*> {
        val existingCar = carRepository.findById(id)
        return if (existingCar != null) {
            val updatedCar = existingCar.copy(
                make = request.make,
                model = request.model,
                year = request.year,
                vin = request.vin
            )
            val savedCar = carRepository.save(updatedCar)
            ResponseEntity.ok(savedCar.toDTO())
        } else {
            ResponseEntity.status(404).body(mapOf("error" to "Car with id '$id' not found"))
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete a car",
        description = "Deletes a car by its ID"
    )
    fun deleteCar(@PathVariable id: String): ResponseEntity<*> {
        val deleted = carRepository.delete(id)
        return if (deleted) {
            ResponseEntity.noContent().build<Unit>()
        } else {
            ResponseEntity.status(404).body(mapOf("error" to "Car with id '$id' not found"))
        }
    }

    @PostMapping("/{id}/parts")
    @Operation(
        summary = "Add a part to a car",
        description = "Adds a new part to a specific car"
    )
    fun addPart(@PathVariable id: String, @RequestBody request: AddPartRequest): ResponseEntity<*> {
        val car = carRepository.findById(id)
        return if (car != null) {
            val part = Part(id = UUID.randomUUID().toString(), name = request.partName)
            carPartsChangeService.addCarPart(id, part)
            carRepository.save(car)
            ResponseEntity.created(URI.create("/car/$id/details")).body(mapOf("message" to "Part added successfully"))
        } else {
            ResponseEntity.status(404).body(mapOf("error" to "Car with id '$id' not found"))
        }
    }

    @DeleteMapping("/{id}/parts/{partId}")
    @Operation(
        summary = "Remove a part from a car",
        description = "Removes a part from a specific car by part ID"
    )
    fun removePart(@PathVariable id: String, @PathVariable partId: String): ResponseEntity<*> {
        val car = carRepository.findById(id)
        return if (car != null) {
            carPartsChangeService.removeCarPart(id, partId)
            carRepository.save(car)
            ResponseEntity.noContent().build<Unit>()
        } else {
            ResponseEntity.status(404).body(mapOf("error" to "Car with id '$id' not found"))
        }
    }

    @GetMapping("/{id}/next-changes")
    @Operation(
        summary = "Return the list of next changes sorted by odometer expiration",
        description = "Retrieves a list of upcoming car part changes sorted by their odometer expiration"
    )
    fun getNextPartChanges(@PathVariable id: String): ResponseEntity<*> {
        return try {
            val nextPartChanges = carPartsChangeService.getNextPartChanges(id)
            ResponseEntity.ok(nextPartChanges.map { it.toDto() })
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(404).body(mapOf("error" to "Car with id '${id}' not found"))
        }
    }

    @PostMapping("/set-odometer")
    @Operation(
        summary = "Set odometer reading",
        description = "Sets a new odometer reading for a specific car"
    )
    fun setOdometer(@RequestBody request: SetOdometerRequest): ResponseEntity<*> {
        return if (request.odometerReading > 0L) {
            carPartsChangeService.setOdometerReading(request.carId, request.odometerReading)
            ResponseEntity.ok(mapOf("message" to "Odometer reading set successfully"))
        } else {
            ResponseEntity.status(404).body(mapOf("error" to "Invalid odometer reading '${request.odometerReading}'."))
        }
    }

    @PostMapping("/set-part-change")
    @Operation(
        summary = "Set odometer reading",
        description = "Sets a new odometer reading for a specific car"
    )
    fun setPartChange(@RequestBody request: SetPartChangeRequest): ResponseEntity<*> {
        return try {
            carPartsChangeService.changePart(
                carId = request.carId,
                partId = request.partId,
                odometerExpiration = request.odometerExpiration,
                duration = Duration.ofDays(150)
            )
            ResponseEntity.ok(mapOf("message" to "Odometer reading set successfully"))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(404).body(mapOf("error" to "Car with id '${request.carId}' not found"))
        }
    }
}

data class CreateUpdateCarRequest(
    val make: String,
    val model: String,
    val year: Int,
    val vin: String,
)

data class AddPartRequest(
    val partName: String
)

data class SetOdometerRequest(
    val carId: String,
    val odometerReading: Long
)

data class SetPartChangeRequest(
    val carId: String,
    val partId: String,
    val odometerExpiration: Long,
)
