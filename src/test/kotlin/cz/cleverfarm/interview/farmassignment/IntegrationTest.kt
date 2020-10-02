package cz.cleverfarm.interview.farmassignment

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import cz.cleverfarm.interview.farmassignment.validation.ValidationErrorResponse
import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import org.flywaydb.test.annotation.FlywayTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureEmbeddedDatabase(provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.DOCKER)
@FlywayTest
@Transactional
@AutoConfigureMockMvc
@WithMockUser
internal abstract class IntegrationTest {

    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    @Autowired
    protected lateinit var mockMvc: MockMvc

    protected fun ResultActionsDsl.andExpectValidationError(field: String, message: String? = null) {
        andExpect {
            status { isBadRequest }
            content { validationError(field, message) }
        }
    }

    protected fun validationError(field: String, message: String? = null): ResultMatcher {
        return ResultMatcher { result ->
            val errors = convertResult<ValidationErrorResponse>(result).errors
            errors.containsKey(field) && (message == null || errors.containsValue(message))
        }
    }

    protected final inline fun <reified T> ResultActionsDsl.andReturnConverted(): T {
        val result = this.andReturn()
        return convertResult(result)
    }

    private inline fun <reified T> convertResult(result: MvcResult): T {
        val typeReference = object : TypeReference<T>() {}
        return objectMapper.readValue(result.response.contentAsString, typeReference)
    }
}