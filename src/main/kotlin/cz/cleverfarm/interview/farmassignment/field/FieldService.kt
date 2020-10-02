package cz.cleverfarm.interview.farmassignment.field

import cz.cleverfarm.interview.farmassignment.common.FARM_NOT_FOUND
import cz.cleverfarm.interview.farmassignment.farm.FarmService
import cz.cleverfarm.interview.farmassignment.field.geometry.FieldGeometryService
import cz.cleverfarm.interview.farmassignment.generated.tables.Field.FIELD
import cz.cleverfarm.interview.farmassignment.validation.FormValidationException
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Service
@Transactional
class FieldService @Autowired constructor(
    private val jooq: DSLContext,
    private val farmService: FarmService,
    private val geometryService: FieldGeometryService
) {

    /**
     * Adds new field to the system.
     *
     * @param farmId ID of farm this field belongs to
     * @param field form with field data
     * */
    fun addNewField(farmId: UUID, field: FieldForm): FieldDto {
        val farm = farmService.findFarmById(farmId)
            ?: throw FormValidationException(FARM_NOT_FOUND)

        val borders = geometryService.parseBorders(field.wkt)
        geometryService.validateBorders(borders, farm.country)

        val record = jooq.newRecord(FIELD, field)
            .with(FIELD.ID, UUID.randomUUID())
            .with(FIELD.FARM_ID, farmId)
            .with(FIELD.BORDERS, borders)
            .with(FIELD.CREATED_AT, LocalDateTime.now())
            .with(FIELD.UPDATED_AT, LocalDateTime.now())
        record.store()
        return record.into(FieldDto::class.java).apply { this.farm = farm }
    }

    /**
     * Retrieves field and farm it belongs to.
     *
     * @param farmId ID of farm this field belongs to
     * @param id ID of this field
     *
     * @return retrieved field or `null` if field with given ID was not found in database
     * */
    fun findFieldById(farmId: UUID, id: UUID): FieldDto? {
        val field = jooq.fetchOne(FIELD.where(FIELD.ID.eq(id).and(FIELD.FARM_ID.eq(farmId)))) ?: return null
        return field.into(FieldDto::class.java).apply { this.farm = farmService.findFarmById(field.farmId) }
    }

    /**
     * Updates field. Only name and borders can be updated.
     *
     * @param farmId ID of farm this field belongs to
     * @param id ID of this field
     * @param updatedField form with updated field data
     *
     * @return updated field or `null` if field with given ID was not found in database.
     * @throws FormValidationException if updated borders are invalid
     * */
    fun updateField(farmId: UUID, id: UUID, updatedField: FieldForm): FieldDto? {
        val farm = farmService.findFarmById(farmId)
        val borders = geometryService.parseBorders(updatedField.wkt)
        geometryService.validateBorders(borders, farm!!.country, updatingId = id)

        val updated =
            jooq.update(FIELD).set(
                jooq.newRecord(FIELD, updatedField)
                    .with(FIELD.UPDATED_AT, LocalDateTime.now())
                    .with(FIELD.BORDERS, borders)
            )
                .where(FIELD.ID.eq(id).and(FIELD.FARM_ID.eq(farmId))).execute() > 0
        return if (updated) findFieldById(farmId, id) else null
    }

    /**
     * Deletes field.
     *
     * @param farmId ID of farm this field belongs to
     * @param id ID of this field
     * @return true if field was deleted, false otherwise
     * */
    fun deleteField(farmId: UUID, id: UUID): Boolean {
        return jooq.deleteFrom(FIELD).where(FIELD.ID.eq(id).and(FIELD.FARM_ID.eq(farmId))).execute() > 0
    }
}