package com.smartfuture.mycarkeeper.infra.jpa

import com.smartfuture.mycarkeeper.core.domain.Car
import com.smartfuture.mycarkeeper.core.domain.repositories.CarRepository
import com.smartfuture.mycarkeeper.infra.jpa.mappers.CarDBMapper
import com.smartfuture.mycarkeeper.infra.jpa.repositories.CarJpaRepository
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Repository
@Primary
@Transactional
@ConditionalOnProperty(
    name = ["app.repository.type"],
    havingValue = "jpa",
    matchIfMissing = true
)
class JpaCarRepository(
    private val jpaRepository: CarJpaRepository,
    private val mapper: CarDBMapper
) : CarRepository {

    override fun getAllCars(): List<Car> {
        return jpaRepository.findAll().map { mapper.toDomain(it) }
    }

    override fun findById(carId: String): Car? {
        return jpaRepository.findById(carId).let { oit -> oit.getOrNull()?.let { mapper.toDomain(it) } }
    }

    override fun findByIdWithDetails(carId: String): Car? {
        return jpaRepository.findByIdWithDetails(carId)?.let { mapper.toDomain(it) }
    }

    override fun save(car: Car): Car {
        val entity = mapper.toEntity(car)
        val saved = jpaRepository.save(entity)
        return mapper.toDomain(saved)
    }

    override fun delete(carId: String): Boolean {
        return if (jpaRepository.existsById(carId)) {
            jpaRepository.deleteById(carId)
            true
        } else {
            false
        }
    }
}
