package cz.cleverfarm.interview.farmassignment

import cz.cleverfarm.interview.farmassignment.common.API_ROOT_FARMS
import cz.cleverfarm.interview.farmassignment.common.COUNTRY_NOT_FOUND
import cz.cleverfarm.interview.farmassignment.common.FARM_ID_VARIABLE
import cz.cleverfarm.interview.farmassignment.farm.FarmDto
import cz.cleverfarm.interview.farmassignment.farm.FarmForm
import cz.cleverfarm.interview.farmassignment.farm.FarmService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
import java.util.UUID

internal class FarmIntegrationTest : IntegrationTest() {

    @Autowired
    lateinit var farmService: FarmService

    @Test
    fun testAddNewFarm() {
        fun submitForm(form: FarmForm) = mockMvc.put(API_ROOT_FARMS) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(form)
        }

        val farmForm = FarmForm("New farm", "CZE", "Some note")
        val response = submitForm(farmForm).andExpect {
            status { isOk }
        }.andReturnConverted<FarmDto>()

        assertThat(response).isEqualToComparingOnlyGivenFields(farmForm, "name", "note")
            .hasNoNullFieldsOrProperties()

        submitForm(FarmForm(null, "CZE", null)).andExpectValidationError("name")
        submitForm(FarmForm("AB", "CZE", null)).andExpectValidationError("name")
        submitForm(FarmForm("Test farm", null, null)).andExpectValidationError("country")
        submitForm(FarmForm("Test farm", "XYZ", null)).andExpectValidationError("country", COUNTRY_NOT_FOUND)
    }

    @Test
    fun testFindAllFarms() {
        val testFarmNames = listOf("Test Farm 1", "Test Farm 2", "Test Farm 3")
        testFarmNames.forEach { createFarm(it, "CZE") }

        val response = mockMvc.get(API_ROOT_FARMS).andExpect {
            status { isOk }
        }.andReturnConverted<List<FarmDto>>()

        assertThat(response).extracting<String>(FarmDto::name).containsSubsequence(testFarmNames.reversed())
    }

    @Test
    fun testFindFarm() {
        val testFarm = createFarm("Test Farm", "CZE")

        val response = mockMvc.get(API_ROOT_FARMS + FARM_ID_VARIABLE, testFarm.id)
            .andExpect { status { isOk } }.andReturnConverted<FarmDto>()

        assertThat(response).isEqualToComparingFieldByField(testFarm).hasNoNullFieldsOrProperties()
    }

    @Test
    fun testFindNonExistentFarm() {
        mockMvc.get(API_ROOT_FARMS + FARM_ID_VARIABLE, UUID.randomUUID())
            .andExpect { status { isNotFound } }
    }

    @Test
    fun testUpdateFarm() {
        val testFarm = createFarm("Test Farm", "CZE", "Test Note")
        val updatedForm = FarmForm("Updated Farm", "CZE", "Updated Note")

        val response = mockMvc.post(API_ROOT_FARMS + FARM_ID_VARIABLE, testFarm.id) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updatedForm)
        }.andExpect { status { isOk } }.andReturnConverted<FarmDto>()

        assertThat(response.updatedAt).isNotEqualTo(testFarm.updatedAt)
        assertThat(response).isEqualToComparingOnlyGivenFields(updatedForm, "name", "note", "country")
            .extracting(FarmDto::id).isEqualTo(testFarm.id)
    }

    @Test
    fun testUpdateNonExistentFarm() {
        mockMvc.post(API_ROOT_FARMS + FARM_ID_VARIABLE, UUID.randomUUID()) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(FarmForm("Updated Farm", "CZE", "Updated Note"))
        }.andExpect { status { isNotFound } }
    }

    @Test
    fun testDeleteFarm() {
        val testFarm = createFarm("Test Farm", "CZE")
        mockMvc.delete(API_ROOT_FARMS + FARM_ID_VARIABLE, testFarm.id).andExpect { status { isOk } }
    }

    @Test
    fun testDeleteNonExistentFarm() {
        mockMvc.delete(API_ROOT_FARMS + FARM_ID_VARIABLE, UUID.randomUUID()).andExpect { status { isNotFound } }
    }

    private fun createFarm(name: String, country: String, note: String = "Default note"): FarmDto {
        val farmForm = FarmForm(name, country, note)
        return farmService.addNewFarm(farmForm)
    }
}