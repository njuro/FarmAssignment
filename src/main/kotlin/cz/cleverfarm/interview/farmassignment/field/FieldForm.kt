package cz.cleverfarm.interview.farmassignment.field

import cz.cleverfarm.interview.farmassignment.common.NAME_LENGTH_ERROR
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class FieldForm(
    @field:NotNull @field:Size(min = 3, max = 255, message = NAME_LENGTH_ERROR) val name: String?,
    @field:NotNull val wkt: String?
)