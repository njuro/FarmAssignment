package cz.cleverfarm.interview.farmassignment.field

import cz.cleverfarm.interview.farmassignment.farm.FarmDto
import java.util.*

data class FieldDto(val id: UUID, val name: String) {
    var farm: FarmDto? = null
}