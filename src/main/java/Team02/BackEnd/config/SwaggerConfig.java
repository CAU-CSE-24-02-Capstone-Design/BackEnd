package Team02.BackEnd.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import Team02.BackEnd.jwt.service.JwtService;

@Configuration
public class SwaggerConfig {

    private final JwtService jwtService;

    @Autowired
    public SwaggerConfig(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        String testToken = jwtService.generateTestToken();

        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER).name("Authorization")
                .description("test token: " + testToken);

        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                .addSecurityItem(securityRequirement)
                .info(new Info()
                        .title("Peach API Documentation")
                        .version("1.0")
                        .description("Peach API documentation for the application"));
    }
}
