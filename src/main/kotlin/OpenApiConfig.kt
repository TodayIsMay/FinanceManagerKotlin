import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.springframework.context.annotation.Configuration

@OpenAPIDefinition(info = Info(title = "Finance Manager Api"))
@SecurityScheme(
    name = "basicAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "basic",
    `in` = SecuritySchemeIn.HEADER
)
@Configuration
class OpenApiConfig {

//    @Bean
//    fun customerOpenApi() :OpenAPI {
//        var securitySchemeName = "bearerAuth"
//        var securityRequirement = SecurityRequirement().addList(securitySchemeName)
//        return OpenAPI().addSecurityItem(securityRequirement).components(Components()
//            .addSecuritySchemes(securitySchemeName, SecurityScheme()
//                .name(securitySchemeName)
//                .type(SecurityScheme.Type.HTTP)
//                .scheme("bearer").bearerFormat("JWT")))
//    }
}