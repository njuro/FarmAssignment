package cz.cleverfarm.interview.farmassignment.field

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.vividsolutions.jts.geom.Geometry
import cz.cleverfarm.interview.farmassignment.farm.FarmDto
import cz.cleverfarm.interview.farmassignment.utils.GeometrySerializer
import java.time.OffsetDateTime
import java.util.*

data class FieldDto(val id: UUID, val name: String, @field:JsonSerialize(using = GeometrySerializer::class) val geom: Geometry, val createdAt: OffsetDateTime, val updatedAt: OffsetDateTime) {
    var farm: FarmDto? = null
}