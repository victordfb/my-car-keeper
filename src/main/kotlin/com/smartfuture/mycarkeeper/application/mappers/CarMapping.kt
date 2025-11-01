package com.smartfuture.mycarkeeper.application.mappers

import com.smartfuture.mycarkeeper.application.dto.CarDTO
import com.smartfuture.mycarkeeper.core.domain.Car
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface CarMapping {

    fun toDto(car: Car): CarDTO

    fun toEntity(dto: CarDTO): Car

    fun toDtoList(cars: List<Car>): List<CarDTO>
}

fun Car.toDTO(): CarDTO = Mappers.getMapper(CarMapping::class.java).toDto(this)
