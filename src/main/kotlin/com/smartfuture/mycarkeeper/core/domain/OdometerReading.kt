package com.smartfuture.mycarkeeper.core.domain

import java.time.LocalDateTime

data class OdometerReading(
    private val id: String,
    private val dataTime: LocalDateTime,
    private val reading: Long
) {

    fun odometer() = reading
}
