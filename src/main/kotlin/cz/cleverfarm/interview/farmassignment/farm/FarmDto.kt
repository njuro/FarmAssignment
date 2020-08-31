package cz.cleverfarm.interview.farmassignment.farm

import cz.cleverfarm.interview.farmassignment.field.FieldDto
import java.time.OffsetDateTime
import java.util.ArrayList
import java.util.UUID

/** DTO representing farm. */
data class FarmDto(
    /** Unique identifier of farm. */
    val id: UUID,

    /** Name of farm. */
    val name: String,

    /** (Optional) note for farm. */
    val note: String?,

    /** Country the farm is located in. Must be ISO 3 (ISO 3166-1 alpha-3) code of country with border information stored in database. */
    val country: String,

    /** Date and time when this farm was added to the system. */
    val createdAt: OffsetDateTime,

    /** Date and time when this farm was last updated. */
    val updatedAt: OffsetDateTime
) {
    /** List of fields belonging to this farm. */
    var fields: List<FieldDto> = ArrayList()
}