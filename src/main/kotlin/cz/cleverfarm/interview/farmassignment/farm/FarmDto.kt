package cz.cleverfarm.interview.farmassignment.farm

import cz.cleverfarm.interview.farmassignment.field.FieldDto
import java.time.OffsetDateTime
import java.util.ArrayList
import java.util.UUID

data class FarmDto(
    val id: UUID,
    val name: String,
    val note: String?,
    val country: String,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime
) {
    var fields: List<FieldDto> = ArrayList()
}