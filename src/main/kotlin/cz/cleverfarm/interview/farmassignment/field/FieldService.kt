package cz.cleverfarm.interview.farmassignment.field

import cz.cleverfarm.interview.farmassignment.common.FARM_NOT_FOUND
import cz.cleverfarm.interview.farmassignment.farm.FarmService
import cz.cleverfarm.interview.farmassignment.generated.tables.Field.FIELD
import cz.cleverfarm.interview.farmassignment.validation.FormValidationException
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime
import java.util.UUID

@Service
@Transactional
class FieldService @Autowired constructor(
    private val jooq: DSLContext,
    private val farmService: FarmService,
    private val geometryService: FieldGeometryService
) {

    fun addNewField(farmId: UUID, field: FieldForm): FieldDto {
        val geometry = geometryService.parseGeometry(field.wkt)
        geometryService.validateGeometry(geometry)

        val farm = farmService.findFarmById(farmId)
            ?: throw FormValidationException(FARM_NOT_FOUND)
        val record = jooq.newRecord(FIELD, field)
            .with(FIELD.ID, UUID.randomUUID())
            .with(FIELD.FARM_ID, farmId)
            .with(FIELD.GEOM, geometry)
            .with(FIELD.CREATED_AT, OffsetDateTime.now())
            .with(FIELD.UPDATED_AT, OffsetDateTime.now())
        record.store()
        return record.into(FieldDto::class.java).apply { this.farm = farm }
    }

    fun findFieldById(farmId: UUID, id: UUID): FieldDto? {
        val field = jooq.fetchOne(FIELD.where(FIELD.ID.eq(id).and(FIELD.FARM_ID.eq(farmId)))) ?: return null
        return field.into(FieldDto::class.java).apply { this.farm = farmService.findFarmById(field.farmId) }
    }

    fun updateField(farmId: UUID, id: UUID, updatedField: FieldForm): FieldDto? {
        val updated =
            jooq.update(FIELD).set(jooq.newRecord(FIELD, updatedField).with(FIELD.UPDATED_AT, OffsetDateTime.now()))
                .where(FIELD.ID.eq(id).and(FIELD.FARM_ID.eq(farmId))).execute() > 0
        return if (updated) findFieldById(farmId, id) else null
    }

    fun deleteField(farmId: UUID, id: UUID): Boolean {
        return jooq.deleteFrom(FIELD).where(FIELD.ID.eq(id).and(FIELD.FARM_ID.eq(farmId))).execute() > 0
    }
}