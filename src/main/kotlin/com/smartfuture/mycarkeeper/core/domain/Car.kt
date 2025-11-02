package com.smartfuture.mycarkeeper.core.domain

data class Car(
    val id: String,
    val make: String,
    val model: String,
    val year: Int,
    val vin: String,
    val parts: List<Part>
)
