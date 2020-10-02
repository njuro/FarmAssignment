package cz.cleverfarm.interview.farmassignment.field

import cz.cleverfarm.interview.farmassignment.common.NAME_LENGTH_ERROR
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

/** Form for creating/updating field. */
data class FieldForm(
    /** Name of the field */
    @field:NotNull @field:Size(min = 3, max = 255, message = NAME_LENGTH_ERROR) val name: String?,

    /** WKT representation of field borders. */
    @field:NotNull val wkt: String?
)