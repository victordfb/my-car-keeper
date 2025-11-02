package com.smartfuture.mycarkeeper.application.dto

import java.time.LocalDateTime

data class PartChangeDTO(
    val carId: String,
    val partId: String,
    val moment: LocalDateTime,
    val expirationDate: LocalDateTime,
    val expirationOdometer: Long,
)
