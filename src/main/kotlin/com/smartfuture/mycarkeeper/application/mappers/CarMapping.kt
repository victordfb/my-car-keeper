package com.smartfuture.mycarkeeper.application.mappers

import com.smartfuture.mycarkeeper.application.dto.CarDTO
import com.smartfuture.mycarkeeper.core.domain.Car
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named
import org.mapstruct.factory.Mappers

@Mapper
abstract class CarMapping {

    @Mapping(source = "car", target = "odometer", qualifiedByName = ["mapOdometer"])
    abstract fun toDto(car: Car): CarDTO

    abstract fun toEntity(dto: CarDTO): Car

    abstract fun toDtoList(cars: List<Car>): List<CarDTO>

    @Named("mapOdometer")
    fun mapOdometer(car: Car): Long = car.odometerReadings.lastOrNull()?.odometer() ?: 0L
}

fun Car.toDTO(): CarDTO = Mappers.getMapper(CarMapping::class.java).toDto(this)
