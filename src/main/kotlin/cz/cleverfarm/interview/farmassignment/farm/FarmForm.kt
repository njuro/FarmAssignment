package cz.cleverfarm.interview.farmassignment.farm

import cz.cleverfarm.interview.farmassignment.common.NAME_LENGTH_ERROR
import cz.cleverfarm.interview.farmassignment.common.NOTE_LENGTH_ERROR
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

/** Form for creating/updating a farm. */
data class FarmForm(
    /** [FarmDto.name] */
    @field:NotNull @field:Size(min = 3, max = 255, message = NAME_LENGTH_ERROR) val name: String?,

    /** [FarmDto.country] */
    @field:NotNull val country: String?,

    /** [FarmDto.note] */
    @field:Size(max = 255, message = NOTE_LENGTH_ERROR) val note: String?
)