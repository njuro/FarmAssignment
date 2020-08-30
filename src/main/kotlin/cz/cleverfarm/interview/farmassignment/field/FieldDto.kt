package cz.cleverfarm.interview.farmassignment.field

import cz.cleverfarm.interview.farmassignment.farm.FarmDto
import java.time.OffsetDateTime
import java.util.*

data class FieldDto(val id: UUID, val name: String, val createdAt: OffsetDateTime, val updatedAt: OffsetDateTime) {
    var farm: FarmDto? = null
}