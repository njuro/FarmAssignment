package cz.cleverfarm.interview.farmassignment.farm

import cz.cleverfarm.interview.farmassignment.field.FieldDto
import java.util.*

data class FarmDto(val id: UUID, val name: String, val note: String?) {
    var fields: List<FieldDto> = ArrayList()
}