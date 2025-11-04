package com.smartfuture.mycarkeeper.infra.jpa.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "odometer_readings")
data class OdometerReadingDB(
    @Id
    @Column(name = "id", length = 255)
    var id: String = "",

    @Column(name = "data_time", nullable = false)
    var dataTime: LocalDateTime = LocalDateTime.now(),

    @Column(name = "reading", nullable = false)
    var reading: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    var car: CarDB? = null
)