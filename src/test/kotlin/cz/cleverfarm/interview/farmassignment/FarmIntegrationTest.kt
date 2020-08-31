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

@SpringBootTest
@Transactional
class FarmIntegrationTest : IntegrationTest() {

    @Autowired
    lateinit var farmService: FarmService

    @Test
    fun testAddNewFarm() {
        val farmForm = FarmForm("New farm", "Some note")
        val response = mockMvc.put(API_ROOT_FARMS) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(farmForm)
        }.andExpect {
            status { isOk }
        }.andReturnConverted<FarmDto>()

        assertThat(response).isEqualToComparingOnlyGivenFields(farmForm, "name", "note")
            .hasNoNullFieldsOrProperties()
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
    fun testDeleteFarm() {
        val testFarm = createFarm("Test Farm")
        mockMvc.delete(API_ROOT_FARMS + FARM_ID_VARIABLE, testFarm.id).andExpect { status { isOk } }
    }

    private fun createFarm(name: String, note: String = "Default note"): FarmDto {
        val farmForm = FarmForm(name, note)
        return farmService.addNewFarm(farmForm)
    }
}