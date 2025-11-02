package com.smartfuture.mycarkeeper.core.domain

import java.time.Duration
import java.time.LocalDateTime

data class PartChange(
    val carId: String,
    val partId: String,
    val moment: LocalDateTime,
    val odometer: Long,
    val odometerExpiration: Long,
    val duration: Duration,
) {
    val expirationDate: LocalDateTime
        get() = moment.plus(duration)

    val expirationOdometer: Long
        get() = odometer + odometerExpiration
}
