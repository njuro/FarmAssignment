package cz.cleverfarm.interview.farmassignment

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl

@AutoConfigureMockMvc
abstract class IntegrationTest {

    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    @Autowired
    protected lateinit var mockMvc: MockMvc

    protected inline fun <reified T> ResultActionsDsl.andReturnConverted(): T {
        val result = this.andReturn()
        val typeReference = object : TypeReference<T>() {}
        return objectMapper.readValue(result.response.contentAsString, typeReference)
    }
}