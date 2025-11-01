package com.smartfuture.mycarkeeper.application.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("My Car Keeper API")
                    .version("1.0.0")
                    .description("API documentation for My Car Keeper application - manage your cars and maintenance records")
                    .contact(
                        Contact()
                            .name("Smart Future")
                            .email("support@smartfuture.com")
                    )
                    .license(
                        License()
                            .name("Apache 2.0")
                            .url("https://www.apache.org/licenses/LICENSE-2.0")
                    )
            )
    }
}
