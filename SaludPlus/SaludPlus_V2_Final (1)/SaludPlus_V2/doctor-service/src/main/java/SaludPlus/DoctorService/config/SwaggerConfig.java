package SaludPlus.DoctorService.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SaludPlus API - Doctor Service")
                        .version("1.0")
                        .description("Documentacion de la API del microservicio doctor-service"));
    }
}
