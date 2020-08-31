package cz.cleverfarm.interview.farmassignment

import cz.cleverfarm.interview.farmassignment.common.API_ROOT_FARMS
import cz.cleverfarm.interview.farmassignment.common.FARM_ID_VARIABLE
import cz.cleverfarm.interview.farmassignment.farm.FarmDto
import cz.cleverfarm.interview.farmassignment.farm.FarmForm
import cz.cleverfarm.interview.farmassignment.farm.FarmService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@SpringBootTest
@Transactional
internal class FarmIntegrationTest : IntegrationTest() {

    @Autowired
    lateinit var farmService: FarmService

    @Test
    fun testAddNewFarm() {
        fun submitForm(form: FarmForm) = mockMvc.put(API_ROOT_FARMS) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(form)
        }

        val farmForm = FarmForm("New farm", "Some note")
        val response = submitForm(farmForm).andExpect {
            status { isOk }
        }.andReturnConverted<FarmDto>()

        assertThat(response).isEqualToComparingOnlyGivenFields(farmForm, "name", "note")
            .hasNoNullFieldsOrProperties()

        submitForm(FarmForm(null, null)).andExpectValidationError("name")
        submitForm(FarmForm("AB", null)).andExpectValidationError("name")
    }

    @Test
    fun testFindAllFarms() {
        val testFarmNames = listOf("Test Farm 1", "Test Farm 2", "Test Farm 3")
        testFarmNames.forEach { createFarm(it) }

        val response = mockMvc.get(API_ROOT_FARMS).andExpect {
            status { isOk }
        }.andReturnConverted<List<FarmDto>>()

        assertThat(response).extracting<String>(FarmDto::name).containsSubsequence(testFarmNames.reversed())
    }

    @Test
    fun testFindFarm() {
        val testFarm = createFarm("Test Farm")

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
        val testFarm = createFarm("Test Farm", "Test Note")
        val updatedForm = FarmForm("Updated Farm", "Updated Note")

        val response = mockMvc.post(API_ROOT_FARMS + FARM_ID_VARIABLE, testFarm.id) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updatedForm)
        }.andExpect { status { isOk } }.andReturnConverted<FarmDto>()

        assertThat(response.updatedAt).isNotEqualTo(testFarm.updatedAt)
        assertThat(response).isEqualToComparingOnlyGivenFields(updatedForm, "name", "note")
            .extracting(FarmDto::id).isEqualTo(testFarm.id)
    }

    @Test
    fun testUpdateNonExistentFarm() {
        mockMvc.post(API_ROOT_FARMS + FARM_ID_VARIABLE, UUID.randomUUID()) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(FarmForm("Updated Farm", "Updated Note"))
        }.andExpect { status { isNotFound } }
    }

    @Test
    fun testDeleteFarm() {
        val testFarm = createFarm("Test Farm")
        mockMvc.delete(API_ROOT_FARMS + FARM_ID_VARIABLE, testFarm.id).andExpect { status { isOk } }
    }

    @Test
    fun testDeleteNonExistentFarm() {
        mockMvc.delete(API_ROOT_FARMS + FARM_ID_VARIABLE, UUID.randomUUID()).andExpect { status { isNotFound } }
    }

    private fun createFarm(name: String, note: String = "Default note"): FarmDto {
        val farmForm = FarmForm(name, note)
        return farmService.addNewFarm(farmForm)
    }
}