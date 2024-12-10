package lk.ac.iit.event_ticketing_system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow all endpoints to be accessed by the React app running on localhost:3000
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")  // React's frontend URL
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Allow specific HTTP methods
               // .allowedHeaders("*")  // Allow all headers
                .allowCredentials(true);  // Allow credentials (cookies, HTTP authentication)
    }
}
