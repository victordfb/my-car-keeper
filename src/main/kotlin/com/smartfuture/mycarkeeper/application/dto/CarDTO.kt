package com.smartfuture.mycarkeeper.application.dto

data class CarDTO(
    val id: String,
    val make: String,
    val model: String,
    val year: Int,
    val vin: String,
    val odometer: Long,
    val parts: List<PartDTO>
)
