package cz.cleverfarm.interview.farmassignment.field

import cz.cleverfarm.interview.farmassignment.farm.FarmDto
import java.util.*

data class FieldDto(var id: UUID, var name: String) {
    var farm: FarmDto? = null
}