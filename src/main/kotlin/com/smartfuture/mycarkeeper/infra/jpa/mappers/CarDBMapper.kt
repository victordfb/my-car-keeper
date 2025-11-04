package com.smartfuture.mycarkeeper.infra.jpa.mappers

import com.smartfuture.mycarkeeper.core.domain.Car
import com.smartfuture.mycarkeeper.core.domain.OdometerReading
import com.smartfuture.mycarkeeper.core.domain.Part
import com.smartfuture.mycarkeeper.core.domain.PartChange
import com.smartfuture.mycarkeeper.infra.jpa.entities.CarDB
import com.smartfuture.mycarkeeper.infra.jpa.entities.OdometerReadingDB
import com.smartfuture.mycarkeeper.infra.jpa.entities.PartChangeDB
import com.smartfuture.mycarkeeper.infra.jpa.entities.PartDB
import org.springframework.stereotype.Component

@Component
class CarDBMapper {

    fun toDomain(entity: CarDB): Car {
        return Car(
            id = entity.id,
            make = entity.make,
            model = entity.model,
            year = entity.year,
            vin = entity.vin,
            parts = entity.parts.map { partToDomain(it) }.toMutableList(),
            odometerReadings = entity.odometerReadings.map { odometerReadingToDomain(it) }.toMutableList(),
            partChanges = entity.partChanges.map { partChangeToDomain(it) }.toMutableList()
        )
    }

    fun toEntity(domain: Car): CarDB {
        val carDB = CarDB(
            id = domain.id,
            make = domain.make,
            model = domain.model,
            year = domain.year,
            vin = domain.vin
        )

        carDB.parts = domain.parts.map { partToEntity(it, carDB) }.toMutableList()
        carDB.odometerReadings = domain.odometerReadings.map { odometerReadingToEntity(it, carDB) }.toMutableList()
        carDB.partChanges = domain.partChanges.map { partChangeToEntity(it, carDB) }.toMutableList()

        return carDB
    }

    private fun partToDomain(entity: PartDB): Part {
        return Part(
            id = entity.id,
            name = entity.name
        )
    }

    private fun partToEntity(domain: Part, car: CarDB): PartDB {
        return PartDB(
            id = domain.id,
            name = domain.name,
            car = car
        )
    }

    private fun odometerReadingToDomain(entity: OdometerReadingDB): OdometerReading {
        return OdometerReading(
            id = entity.id,
            dataTime = entity.dataTime,
            reading = entity.reading
        )
    }

    private fun odometerReadingToEntity(domain: OdometerReading, car: CarDB): OdometerReadingDB {
        return OdometerReadingDB(
            id = domain.id,
            dataTime = domain.dataTime,
            reading = domain.reading,
            car = car
        )
    }

    private fun partChangeToDomain(entity: PartChangeDB): PartChange {
        return PartChange(
            carId = entity.car?.id ?: "",
            partId = entity.partId,
            moment = entity.moment,
            odometer = entity.odometer,
            odometerExpiration = entity.odometerExpiration,
            duration = entity.duration
        )
    }

    private fun partChangeToEntity(domain: PartChange, car: CarDB): PartChangeDB {
        return PartChangeDB(
            car = car,
            partId = domain.partId,
            moment = domain.moment,
            odometer = domain.odometer,
            odometerExpiration = domain.odometerExpiration,
            durationSeconds = domain.duration.seconds
        )
    }
}
