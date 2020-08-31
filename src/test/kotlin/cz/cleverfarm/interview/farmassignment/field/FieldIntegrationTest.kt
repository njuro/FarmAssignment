package cz.cleverfarm.interview.farmassignment.field

import cz.cleverfarm.interview.farmassignment.IntegrationTest
import cz.cleverfarm.interview.farmassignment.common.API_ROOT_FIELDS
import cz.cleverfarm.interview.farmassignment.common.FIELD_ID_VARIABLE
import cz.cleverfarm.interview.farmassignment.farm.FarmDto
import cz.cleverfarm.interview.farmassignment.farm.FarmForm
import cz.cleverfarm.interview.farmassignment.farm.FarmService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
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
class FieldIntegrationTest : IntegrationTest() {

    val TEST_WKT =
        "POLYGON((16.845748637978172 48.961109736844335,17.010543559853172 48.961109736844335,17.010543559853172 48.86002119708477,16.845748637978172 48.86002119708477,16.845748637978172 48.961109736844335))"
    val TEST_WKT_OVERLAP =
        "POLYGON((16.922652934853172 48.93766098229502,17.000930522743797 48.93766098229502,17.000930522743797 48.868151941067616,16.922652934853172 48.868151941067616,16.922652934853172 48.93766098229502))"

    @Autowired
    lateinit var farmService: FarmService

    @Autowired
    lateinit var fieldService: FieldService

    lateinit var farm: FarmDto

    @BeforeEach
    fun setUp() {
        farm = farmService.addNewFarm(FarmForm("Test Farm", null))
    }

    @Test
    fun testAddNewField() {
        val fieldForm = FieldForm(
            "Test Field", TEST_WKT
        )
        val response = mockMvc.put(API_ROOT_FIELDS, farm.id) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(fieldForm)
        }.andExpect { status { isOk } }.andReturnConverted<FieldDto>()


        assertThat(response.name).isEqualTo(fieldForm.name)
        assertThat(response.geom.toText()).isEqualToIgnoringWhitespace(fieldForm.wkt)
        assertThat(response).hasNoNullFieldsOrProperties()
    }

    @Test
    fun testFindField() {
        val testField = createField("Test Field", TEST_WKT)

        val response = mockMvc.get(API_ROOT_FIELDS + FIELD_ID_VARIABLE, farm.id, testField.id)
            .andExpect { status { isOk } }
            .andReturnConverted<FieldDto>()

        assertThat(response).isEqualToComparingFieldByField(testField)
    }

    @Test
    fun updateField() {
        val testField = createField("Test Field", TEST_WKT)
        val updatedForm = FieldForm("Updated Field", TEST_WKT_OVERLAP)

        val response = mockMvc.post(API_ROOT_FIELDS + FIELD_ID_VARIABLE, farm.id, testField.id) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updatedForm)
        }.andExpect { status { isOk } }.andReturnConverted<FieldDto>()

        assertThat(response.name).isEqualTo(updatedForm.name)
        assertThat(response.geom.toText()).isEqualToIgnoringWhitespace(updatedForm.wkt)
        assertThat(response.updatedAt).isNotEqualTo(testField.updatedAt)
        assertThat(response).isEqualToComparingOnlyGivenFields(testField, "id", "createdAt", "farm")
            .hasNoNullFieldsOrProperties()
    }

    @Test
    fun deleteField() {
        val testField = createField("Test Field", TEST_WKT)
        mockMvc.delete(API_ROOT_FIELDS + FIELD_ID_VARIABLE, farm.id, testField.id).andExpect { status { isOk } }
    }

    private fun createField(name: String, wkt: String): FieldDto {
        return fieldService.addNewField(farm.id, FieldForm(name, wkt))
    }
}