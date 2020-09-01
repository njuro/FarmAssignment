package cz.cleverfarm.interview.farmassignment.config

import com.vividsolutions.jts.geom.Geometry
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfiguration {

    /** Configuration for Swagger2 API documentation provided by SpringFox. */
    @Bean
    fun api(): Docket? {
        return Docket(DocumentationType.SWAGGER_2)
            .directModelSubstitute(Geometry::class.java, String::class.java)
            .select()
            .apis(RequestHandlerSelectors.basePackage("cz.cleverfarm.interview.farmassignment"))
            .paths(PathSelectors.any())
            .build()
    }
}