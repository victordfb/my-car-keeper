package com.smartfuture.mycarkeeper.application.mappers

import com.smartfuture.mycarkeeper.application.dto.PartChangeDTO
import com.smartfuture.mycarkeeper.core.domain.PartChange
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface PartChangeMapping {
    fun toDto(partChange: PartChange): PartChangeDTO
    fun toEntity(partChangeDTO: PartChangeDTO): PartChange
}

fun PartChange.toDto(): PartChangeDTO = Mappers.getMapper(PartChangeMapping::class.java).toDto(this)
