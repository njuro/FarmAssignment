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

    /**
     * Adds new farm to the system.
     *
     * @param farm form with farm data
     * @return created farm
     * */
    fun addNewFarm(farm: FarmForm): FarmDto {
        validateCountryCode(farm.country!!)

        val record = jooq.newRecord(FARM, farm)
            .with(FARM.ID, UUID.randomUUID())
            .with(FARM.COUNTRY, farm.country.toUpperCase())
            .with(FARM.CREATED_AT, LocalDateTime.now())
            .with(FARM.UPDATED_AT, LocalDateTime.now())
        record.store()
        return record.into(FarmDto::class.java)
    }

    /**
     * Retrieves list of farms and their fields in the system with pagination applied.
     *
     * @param page which results page to return. Zero-based indexing.
     * @param pageSize how many results per one page
     * */
    fun findAllFarms(page: Int, pageSize: Int): List<FarmDto> {
        val farms =
            jooq.selectFrom(FARM).orderBy(FARM.UPDATED_AT.desc()).offset(page * pageSize).limit(pageSize)
                .fetchInto(FarmDto::class.java)
        val fields = jooq.fetch(FIELD).intoGroups(FIELD.FARM_ID, FieldDto::class.java)
        farms.forEach {
            it.fields = fields.getOrDefault(it.id, listOf()).sortedByDescending { field -> field.updatedAt }
        }
        return farms
    }

    /**
     * Retrieves farm.
     *
     * @param id unique identifier of the farm
     * @param fetchFields if true, also returns fields of the farm
     * @return retrieved farm, or `null` if none was found with given ID
     * */
    fun findFarmById(id: UUID, fetchFields: Boolean = false): FarmDto? {
        val farmRecord = jooq.fetchOne(FARM.where(FARM.ID.eq(id))) ?: return null
        val farm = farmRecord.into(FarmDto::class.java)
        if (fetchFields) {
            farm.fields = jooq.selectFrom(FIELD).where(FIELD.FARM_ID.eq(farm.id)).orderBy(FIELD.UPDATED_AT.desc())
                .fetchInto(FieldDto::class.java)
        }

        return farm
    }

    /**
     * Updates farm.
     *
     * @param id unique identifier of the farm
     * @param updatedFarm form with updated values (only name and note can be updated)
     * @return updated farm or `null` if no farm was found with given ID
     *
     * */
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

    /**
     *  Deletes farm and all its fields.
     *
     *  @param id unique identifier of the farm
     *  @return true if farm was deleted, false otherwise
     * */
    fun deleteFarm(id: UUID): Boolean {
        jooq.deleteFrom(FIELD).where(FIELD.FARM_ID.eq(id)).execute()
        return jooq.deleteFrom(FARM).where(FARM.ID.eq(id)).execute() > 0
    }

    /**
     * Validates country code of farm.
     *
     * @throws FormValidationException if country code is not registered in the database
     * */
    private fun validateCountryCode(countryCode: String) {
        val countryExists = jooq.select(count()).from(COUNTRY).where(COUNTRY.CODE.equalIgnoreCase(countryCode))
            .fetchOneInto(Integer::class.java) > 0
        if (!countryExists) {
            throw FormValidationException("country", COUNTRY_NOT_FOUND)
        }
    }
}