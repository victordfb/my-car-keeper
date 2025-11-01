package com.smartfuture.mycarkeeper.application.dto

data class CarDTO(
    val id: Long,
    val make: String,
    val model: String,
    val year: Int,
    val vin: String
)
