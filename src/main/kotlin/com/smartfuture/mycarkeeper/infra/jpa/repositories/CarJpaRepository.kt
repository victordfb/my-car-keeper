package com.smartfuture.mycarkeeper.infra.jpa.repositories

import com.smartfuture.mycarkeeper.infra.jpa.entities.CarDB
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CarJpaRepository : JpaRepository<CarDB, String> {

    @Query("SELECT c FROM CarDB c LEFT JOIN FETCH c.parts WHERE c.id = :id")
    fun findByIdWithDetails(id: String): CarDB?
}