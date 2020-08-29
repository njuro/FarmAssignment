package cz.cleverfarm.interview.farmassignment.farm

import cz.cleverfarm.interview.farmassignment.field.FieldDto
import cz.cleverfarm.interview.farmassignment.generated.tables.Farm.FARM
import cz.cleverfarm.interview.farmassignment.generated.tables.Field.FIELD
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.ArrayList

@Service
class FarmService @Autowired constructor(private val jooq: DSLContext) {

    fun addNewFarm(farm: FarmForm): FarmDto {
        val record = jooq.newRecord(FARM, farm).with(FARM.ID, UUID.randomUUID())
        record.store()
        return record.into(FarmDto::class.java)
    }

    fun findAllFarms(): List<FarmDto> {
        val farms = jooq.fetch(FARM).into(FarmDto::class.java)
        val fields = jooq.fetch(FIELD).intoGroups(FIELD.FARM_ID, FieldDto::class.java)
        farms.forEach { it.fields = fields.getOrDefault(it.id, ArrayList()) }
        return farms
    }

    fun findFarmById(id: UUID): FarmDto? {
        val farm = jooq.fetchOne(FARM.where(FARM.ID.eq(id))).into(FarmDto::class.java) ?: return null
        farm.fields = jooq.fetch(FIELD.where(FIELD.FARM_ID.eq(farm.id))).into(FieldDto::class.java)
        return farm
    }

    fun updateFarm(id: UUID, updatedFarm: FarmForm): FarmDto? {
        val updated = jooq.update(FARM).set(jooq.newRecord(FARM, updatedFarm)).where(FARM.ID.eq(id)).execute() > 0
        return if (updated) findFarmById(id) else null
    }

    fun deleteFarm(id: UUID): Boolean {
        jooq.deleteFrom(FIELD).where(FIELD.FARM_ID.eq(id)).execute()
        return jooq.deleteFrom(FARM).where(FARM.ID.eq(id)).execute() > 0
    }
}