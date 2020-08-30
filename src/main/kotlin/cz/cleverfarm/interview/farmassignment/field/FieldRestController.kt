package cz.cleverfarm.interview.farmassignment.field

import cz.cleverfarm.interview.farmassignment.common.API_ROOT_FIELDS
import cz.cleverfarm.interview.farmassignment.common.FARM_ID_VARIABLE_NAME
import cz.cleverfarm.interview.farmassignment.common.FIELD_ID_VARIABLE
import cz.cleverfarm.interview.farmassignment.common.FIELD_ID_VARIABLE_NAME
import cz.cleverfarm.interview.farmassignment.common.FIELD_NOT_FOUND
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.UUID
import javax.validation.Valid

@RestController
@RequestMapping(API_ROOT_FIELDS)
class FieldRestController @Autowired constructor(private val fieldService: FieldService) {

    @PutMapping
    fun addNewField(
        @PathVariable(name = FARM_ID_VARIABLE_NAME) farmId: UUID,
        @Valid @RequestBody field: FieldForm
    ): FieldDto {
        return fieldService.addNewField(farmId, field)
    }

    @GetMapping(FIELD_ID_VARIABLE)
    fun findField(
        @PathVariable(name = FARM_ID_VARIABLE_NAME) farmId: UUID,
        @PathVariable(name = FIELD_ID_VARIABLE_NAME) fieldId: UUID
    ): FieldDto {
        return fieldService.findFieldById(farmId, fieldId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, FIELD_NOT_FOUND)
    }

    @PostMapping(FIELD_ID_VARIABLE)
    fun updateField(
        @PathVariable(name = FARM_ID_VARIABLE_NAME) farmId: UUID,
        @PathVariable(name = FIELD_ID_VARIABLE_NAME) fieldId: UUID,
        @Valid @RequestBody updatedField: FieldForm
    ): FieldDto {
        return fieldService.updateField(farmId, fieldId, updatedField)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, FIELD_NOT_FOUND)
    }

    @DeleteMapping(FIELD_ID_VARIABLE)
    fun deleteField(
        @PathVariable(name = FARM_ID_VARIABLE_NAME) farmId: UUID,
        @PathVariable(name = FIELD_ID_VARIABLE_NAME) fieldId: UUID
    ) {
        fieldService.deleteField(farmId, fieldId) || throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            FIELD_NOT_FOUND
        )
    }
}