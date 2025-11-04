package com.smartfuture.mycarkeeper.core.domain

import java.time.LocalDateTime

data class OdometerReading(
    val id: String,
    val dataTime: LocalDateTime,
    val reading: Long
) {

    fun odometer() = reading
}
