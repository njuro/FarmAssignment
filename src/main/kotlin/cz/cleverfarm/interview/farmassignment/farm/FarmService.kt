package cz.cleverfarm.interview.farmassignment.farm

import cz.cleverfarm.interview.farmassignment.field.FieldDto
import cz.cleverfarm.interview.farmassignment.generated.tables.Farm.FARM
import cz.cleverfarm.interview.farmassignment.generated.tables.Field.FIELD
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime
import java.util.*

@Service
@Transactional
class FarmService @Autowired constructor(private val jooq: DSLContext) {

    fun addNewFarm(farm: FarmForm): FarmDto {
        val record = jooq.newRecord(FARM, farm)
                .with(FARM.ID, UUID.randomUUID())
                .with(FARM.CREATED_AT, OffsetDateTime.now())
                .with(FARM.UPDATED_AT, OffsetDateTime.now())
        record.store()
        return record.into(FarmDto::class.java)
    }

    fun findAllFarms(): List<FarmDto> {
        val farms = jooq.selectFrom(FARM).orderBy(FARM.UPDATED_AT.desc()).fetchInto(FarmDto::class.java)
        val fields = jooq.fetch(FIELD).intoGroups(FIELD.FARM_ID, FieldDto::class.java)
        farms.forEach { it.fields = fields.getOrDefault(it.id, listOf()).sortedByDescending { field -> field.updatedAt } }
        return farms
    }

    fun findFarmById(id: UUID, fetchFields: Boolean = false): FarmDto? {
        val farmRecord = jooq.fetchOne(FARM.where(FARM.ID.eq(id))) ?: return null
        val farm = farmRecord.into(FarmDto::class.java)
        if (fetchFields) {
            farm.fields = jooq.selectFrom(FIELD).where(FIELD.FARM_ID.eq(farm.id)).orderBy(FIELD.UPDATED_AT.desc()).fetchInto(FieldDto::class.java)
        }

        return farm
    }

    fun updateFarm(id: UUID, updatedFarm: FarmForm): FarmDto? {
        val updated = jooq.update(FARM).set(jooq.newRecord(FARM, updatedFarm).with(FARM.UPDATED_AT, OffsetDateTime.now())).where(FARM.ID.eq(id)).execute() > 0
        return if (updated) findFarmById(id, fetchFields = true) else null
    }

    fun deleteFarm(id: UUID): Boolean {
        jooq.deleteFrom(FIELD).where(FIELD.FARM_ID.eq(id)).execute()
        return jooq.deleteFrom(FARM).where(FARM.ID.eq(id)).execute() > 0
    }
}