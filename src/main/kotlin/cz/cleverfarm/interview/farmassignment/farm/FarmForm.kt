package cz.cleverfarm.interview.farmassignment.farm

import cz.cleverfarm.interview.farmassignment.common.NAME_LENGTH_VALIDATION
import cz.cleverfarm.interview.farmassignment.common.NOTE_LENGTH_VALIDATION
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class FarmForm(
    @field:NotNull @field:Size(min = 3, max = 255, message = NAME_LENGTH_VALIDATION) val name: String?,
    @field:Size(max = 255, message = NOTE_LENGTH_VALIDATION) val note: String?
)