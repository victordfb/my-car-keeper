package com.smartfuture.mycarkeeper.infra.jpa.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.Duration
import java.time.LocalDateTime

@Entity
@Table(name = "part_changes")
data class PartChangeDB(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    var car: CarDB? = null,

    @Column(name = "part_id", nullable = false)
    var partId: String = "",

    @Column(name = "moment", nullable = false)
    var moment: LocalDateTime = LocalDateTime.now(),

    @Column(name = "odometer", nullable = false)
    var odometer: Long = 0,

    @Column(name = "odometer_expiration", nullable = false)
    var odometerExpiration: Long = 0,

    @Column(name = "duration_seconds", nullable = false)
    var durationSeconds: Long = 0
) {
    var duration: Duration
        get() = Duration.ofSeconds(durationSeconds)
        set(value) {
            durationSeconds = value.seconds
        }

    val expirationDate: LocalDateTime
        get() = moment.plus(duration)

    val expirationOdometer: Long
        get() = odometer + odometerExpiration
}