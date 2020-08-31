package cz.cleverfarm.interview.farmassignment.field

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.vividsolutions.jts.geom.Geometry
import cz.cleverfarm.interview.farmassignment.farm.FarmDto
import cz.cleverfarm.interview.farmassignment.field.geometry.GeometryDeserializer
import cz.cleverfarm.interview.farmassignment.field.geometry.GeometrySerializer
import java.time.OffsetDateTime
import java.util.UUID

/** DTO for field belonging to farm. */
data class FieldDto(
    /** Unique identifier of this field. */
    val id: UUID,

    /** Name of this field. */
    val name: String,

    /** Borders of this field. */
    @field:JsonSerialize(using = GeometrySerializer::class) @field:JsonDeserialize(using = GeometryDeserializer::class) val borders: Geometry,

    /** Date and time when this field was added to the system. */
    val createdAt: OffsetDateTime,

    /** Date and time when this field was last updated. */
    val updatedAt: OffsetDateTime
) {

    /** Farm this field belongs to. */
    var farm: FarmDto? = null

    /** Area calculated from borders of this field. */
    val area = borders.area
}