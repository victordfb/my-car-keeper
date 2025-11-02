package com.smartfuture.mycarkeeper.core.domain

import java.time.Duration
import java.time.LocalDateTime
import java.util.*

data class Car(
    val id: String,
    val make: String,
    val model: String,
    val year: Int,
    val vin: String,
    val parts: MutableList<Part> = mutableListOf(),
    val odometerReadings: MutableList<OdometerReading> = mutableListOf(),
    val partChanges: MutableList<PartChange> = mutableListOf(),
) {

    fun setOdometerReading(reading: Long) {
        val id = UUID.randomUUID().toString()
        val time = LocalDateTime.now()
        odometerReadings.add(OdometerReading(id, time, reading))
    }

    fun setPartsChange(part: Part, odometerExpiration: Long, duration: Duration) {
        val time = LocalDateTime.now()
        val odometer = currentOdometer().odometer()
        val partChange = PartChange(this.id, part.id, time, odometer, odometerExpiration, duration)
        partChanges.add(partChange)
    }

    fun addPart(part: Part) {
        parts.add(part)
    }

    private fun currentOdometer() = odometerReadings.last()
}
