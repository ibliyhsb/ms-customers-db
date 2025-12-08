package cl.duoc.ms_customers_db.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI apiInfo(){
        return new OpenAPI() 
                   .info(new Info().title("Customers microservice - Database (DB) Documentation.")
                                    .description("This microservice is responsible for client data access and management in the database.")
                                    .version("1.0"));
    }
}
