package com.smartfuture.mycarkeeper.infra.jpa.entities

import jakarta.persistence.*

@Entity
@Table(name = "cars")
data class CarDB(
    @Id
    @Column(name = "id", length = 255)
    var id: String = "",

    @Column(name = "make", nullable = false)
    var make: String = "",

    @Column(name = "model", nullable = false)
    var model: String = "",

    @Column(name = "year", nullable = false)
    var year: Int = 0,

    @Column(name = "vin", nullable = false, unique = true)
    var vin: String = "",

    @OneToMany(mappedBy = "car", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    var parts: MutableList<PartDB> = mutableListOf(),

    @OneToMany(mappedBy = "car", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    var odometerReadings: MutableList<OdometerReadingDB> = mutableListOf(),

    @OneToMany(mappedBy = "car", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    var partChanges: MutableList<PartChangeDB> = mutableListOf()
)