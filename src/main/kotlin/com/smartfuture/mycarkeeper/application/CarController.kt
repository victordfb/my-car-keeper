package com.smartfuture.mycarkeeper.application

import com.smartfuture.mycarkeeper.application.dto.CarDTO
import com.smartfuture.mycarkeeper.application.mappers.toDTO
import com.smartfuture.mycarkeeper.application.mappers.toDto
import com.smartfuture.mycarkeeper.core.domain.repositories.CarRepository
import com.smartfuture.mycarkeeper.core.service.CarPartsChangeService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Duration

@Suppress("unused")
@RestController
@Tag(name = "Cars", description = "Car management endpoints")
class CarController(
    private val carRepository: CarRepository,
    private val carPartsChangeService: CarPartsChangeService,
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

    @GetMapping("/car/{id}/next-changes")
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

    @PostMapping("/car/set-odometer")
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

    @PostMapping("/car/set-part-change")
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

data class SetOdometerRequest(
    val carId: String,
    val odometerReading: Long
)

data class SetPartChangeRequest(
    val carId: String,
    val partId: String,
    val odometerExpiration: Long,
)
