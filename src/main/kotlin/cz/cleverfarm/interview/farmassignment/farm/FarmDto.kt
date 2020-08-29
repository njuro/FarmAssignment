package cz.cleverfarm.interview.farmassignment.farm

import cz.cleverfarm.interview.farmassignment.field.FieldDto
import java.util.*

data class FarmDto(var id: UUID, var name: String, var note: String?) {
    var fields: List<FieldDto> = ArrayList()
}