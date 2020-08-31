package cz.cleverfarm.interview.farmassignment.farm

import cz.cleverfarm.interview.farmassignment.common.COUNTRY_NOT_FOUND
import cz.cleverfarm.interview.farmassignment.field.FieldDto
import cz.cleverfarm.interview.farmassignment.generated.tables.Country.COUNTRY
import cz.cleverfarm.interview.farmassignment.generated.tables.Farm.FARM
import cz.cleverfarm.interview.farmassignment.generated.tables.Field.FIELD
import cz.cleverfarm.interview.farmassignment.validation.FormValidationException
import org.jooq.DSLContext
import org.jooq.impl.DSL.count
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Service
@Transactional
class FarmService @Autowired constructor(private val jooq: DSLContext) {

    fun addNewFarm(farm: FarmForm): FarmDto {
        validateCountry(farm.country!!)

        val record = jooq.newRecord(FARM, farm)
            .with(FARM.ID, UUID.randomUUID())
            .with(FARM.COUNTRY, farm.country.toUpperCase())
            .with(FARM.CREATED_AT, LocalDateTime.now())
            .with(FARM.UPDATED_AT, LocalDateTime.now())
        record.store()
        return record.into(FarmDto::class.java)
    }

    fun findAllFarms(): List<FarmDto> {
        val farms = jooq.selectFrom(FARM).orderBy(FARM.UPDATED_AT.desc()).fetchInto(FarmDto::class.java)
        val fields = jooq.fetch(FIELD).intoGroups(FIELD.FARM_ID, FieldDto::class.java)
        farms.forEach {
            it.fields = fields.getOrDefault(it.id, listOf()).sortedByDescending { field -> field.updatedAt }
        }
        return farms
    }

    fun findFarmById(id: UUID, fetchFields: Boolean = false): FarmDto? {
        val farmRecord = jooq.fetchOne(FARM.where(FARM.ID.eq(id))) ?: return null
        val farm = farmRecord.into(FarmDto::class.java)
        if (fetchFields) {
            farm.fields = jooq.selectFrom(FIELD).where(FIELD.FARM_ID.eq(farm.id)).orderBy(FIELD.UPDATED_AT.desc())
                .fetchInto(FieldDto::class.java)
        }

        return farm
    }

    fun updateFarm(id: UUID, updatedFarm: FarmForm): FarmDto? {
        val updated =
            jooq.update(FARM).set(
                mapOf(
                    FARM.NAME to updatedFarm.name,
                    FARM.NOTE to updatedFarm.note,
                    FARM.UPDATED_AT to LocalDateTime.now()
                )
            )
                .where(FARM.ID.eq(id)).execute() > 0
        return if (updated) findFarmById(id, fetchFields = true) else null
    }

    fun deleteFarm(id: UUID): Boolean {
        jooq.deleteFrom(FIELD).where(FIELD.FARM_ID.eq(id)).execute()
        return jooq.deleteFrom(FARM).where(FARM.ID.eq(id)).execute() > 0
    }

    private fun validateCountry(countryCode: String) {
        val countryExists = jooq.select(count()).from(COUNTRY).where(COUNTRY.CODE.equalIgnoreCase(countryCode))
            .fetchOneInto(Integer::class.java) > 0
        if (!countryExists) {
            throw FormValidationException("country", COUNTRY_NOT_FOUND)
        }
    }
}