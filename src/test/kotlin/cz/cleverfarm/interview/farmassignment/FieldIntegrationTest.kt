package cz.cleverfarm.interview.farmassignment

import cz.cleverfarm.interview.farmassignment.common.API_ROOT_FIELDS
import cz.cleverfarm.interview.farmassignment.common.FARM_NOT_FOUND
import cz.cleverfarm.interview.farmassignment.common.FIELD_ID_VARIABLE
import cz.cleverfarm.interview.farmassignment.common.GEOMETRY_AREA_ERROR
import cz.cleverfarm.interview.farmassignment.common.GEOMETRY_COUNTRY_ERROR
import cz.cleverfarm.interview.farmassignment.common.GEOMETRY_INVALID_ERROR
import cz.cleverfarm.interview.farmassignment.common.GEOMETRY_POLYGON_ERROR
import cz.cleverfarm.interview.farmassignment.common.GEOMETRY_SHAPE_ERROR
import cz.cleverfarm.interview.farmassignment.farm.FarmDto
import cz.cleverfarm.interview.farmassignment.farm.FarmForm
import cz.cleverfarm.interview.farmassignment.farm.FarmService
import cz.cleverfarm.interview.farmassignment.field.FieldDto
import cz.cleverfarm.interview.farmassignment.field.FieldForm
import cz.cleverfarm.interview.farmassignment.field.FieldService
import cz.cleverfarm.interview.farmassignment.validation.OBJECT_ERROR
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
import java.util.UUID

internal class FieldIntegrationTest : IntegrationTest() {

    val TEST_WKT =
        "POLYGON((16.845748637978172 48.961109736844335,17.010543559853172 48.961109736844335,17.010543559853172 48.86002119708477,16.845748637978172 48.86002119708477,16.845748637978172 48.961109736844335))"
    val TEST_WKT_OVERLAP =
        "POLYGON((16.922652934853172 48.93766098229502,17.000930522743797 48.93766098229502,17.000930522743797 48.868151941067616,16.922652934853172 48.868151941067616,16.922652934853172 48.93766098229502))"
    val TEST_WKT_LINESTRING =
        "LINESTRING(15.433608773159747 49.25974176677513,14.708511116909747 49.231053362442616,14.708511116909747 49.38863337029493,15.477554085659747 49.374328740718916)"
    val TEST_WKT_INVALID = "POLYGON((0 0, 10 10, 0 10, 10 0, 0 0))"
    val TEST_WKT_EMPTY = "POLYGON((0 0, 0 0, 0 0, 0 0))"
    val TEST_WKT_OTHER_COUNTRY =
        "POLYGON((9.671474699765662 51.932749866276765,11.802822356015662 51.932749866276765,11.802822356015662 50.710720847340276,9.671474699765662 50.710720847340276,9.671474699765662 51.932749866276765))"

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
        fun submitForm(form: FieldForm) = mockMvc.put(API_ROOT_FIELDS, farm.id) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(form)
        }

        val fieldForm = FieldForm(
            "Test Field", TEST_WKT
        )
        val response = submitForm(fieldForm).andExpect { status { isOk } }.andReturnConverted<FieldDto>()

        assertThat(response.name).isEqualTo(fieldForm.name)
        assertThat(response.geom.toText()).isEqualToIgnoringWhitespace(fieldForm.wkt)
        assertThat(response).hasNoNullFieldsOrProperties()

        submitForm(FieldForm("Test Field", "Invalid WKT")).andExpectValidationError("wkt", GEOMETRY_INVALID_ERROR)
        submitForm(FieldForm("Test Field", TEST_WKT_LINESTRING)).andExpectValidationError("wkt", GEOMETRY_SHAPE_ERROR)
        submitForm(FieldForm("Test Field", TEST_WKT_INVALID)).andExpectValidationError("wkt", GEOMETRY_POLYGON_ERROR)
        submitForm(FieldForm("Test Field", TEST_WKT_EMPTY)).andExpectValidationError("wkt", GEOMETRY_AREA_ERROR)
        // TODO check overlap
        // submitForm(FieldForm("Test Field", TEST_WKT)).andExpectValidationError("wkt", GEOMETRY_OVERLAP_ERROR)
        submitForm(FieldForm("Test Field", TEST_WKT_OTHER_COUNTRY)).andExpectValidationError(
            "wkt",
            GEOMETRY_COUNTRY_ERROR
        )
    }

    @Test
    fun testAddFieldToNonExistentFarm() {
        mockMvc.put(API_ROOT_FIELDS, UUID.randomUUID()) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(FieldForm("Test Field", TEST_WKT))
        }.andExpectValidationError(OBJECT_ERROR, FARM_NOT_FOUND)
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
    fun testFindNonExistentField() {
        mockMvc.get(API_ROOT_FIELDS + FIELD_ID_VARIABLE, farm.id, UUID.randomUUID()).andExpect { status { isNotFound } }
    }

    @Test
    fun testUpdateField() {
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
    fun testUpdateNonExistentField() {
        mockMvc.post(API_ROOT_FIELDS + FIELD_ID_VARIABLE, farm.id, UUID.randomUUID()) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(FieldForm("Updated Field", TEST_WKT_OVERLAP))
        }.andExpect { status { isNotFound } }
    }

    @Test
    fun testDeleteField() {
        val testField = createField("Test Field", TEST_WKT)
        mockMvc.delete(API_ROOT_FIELDS + FIELD_ID_VARIABLE, farm.id, testField.id).andExpect { status { isOk } }
    }

    @Test
    fun testDeleteNonExistentField() {
        mockMvc.delete(API_ROOT_FIELDS + FIELD_ID_VARIABLE, farm.id, UUID.randomUUID())
            .andExpect { status { isNotFound } }
    }

    fun createField(name: String, wkt: String): FieldDto {
        return fieldService.addNewField(farm.id, FieldForm(name, wkt))
    }
}