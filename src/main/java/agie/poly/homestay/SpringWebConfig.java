package agie.poly.homestay;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringWebConfig implements WebMvcConfigurer {

    @Bean
    public AuthenticationConfig authenticationConfig() {
        return new AuthenticationConfig();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationConfig())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/*");
    }
}