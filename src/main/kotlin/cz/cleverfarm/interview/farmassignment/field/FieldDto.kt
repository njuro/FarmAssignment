package cz.cleverfarm.interview.farmassignment.field

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.vividsolutions.jts.geom.Geometry
import cz.cleverfarm.interview.farmassignment.farm.FarmDto
import cz.cleverfarm.interview.farmassignment.field.geometry.GeometryDeserializer
import cz.cleverfarm.interview.farmassignment.field.geometry.GeometrySerializer
import java.time.OffsetDateTime
import java.util.UUID

data class FieldDto(
    val id: UUID,
    val name: String,
    @field:JsonSerialize(using = GeometrySerializer::class) @field:JsonDeserialize(using = GeometryDeserializer::class) val borders: Geometry,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime
) {
    var farm: FarmDto? = null
}