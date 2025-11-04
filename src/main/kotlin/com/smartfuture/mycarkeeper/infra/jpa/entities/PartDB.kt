package com.smartfuture.mycarkeeper.infra.jpa.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "parts")
data class PartDB(
    @Id
    @Column(name = "id", length = 255)
    var id: String = "",

    @Column(name = "name", nullable = false)
    var name: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    var car: CarDB? = null
)