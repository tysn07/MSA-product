package group.microserviceproduct.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 경로에 대해
            .allowedOrigins("http://15.165.174.43:8081","http://15.165.174.43:8082","http://3.39.43.179:8084","https://back.son7shop.com") // 허용할 오리진
            .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메소드
            .allowedHeaders("Authorization", "Content-Type") // 허용할 헤더
            .allowCredentials(true) // 쿠키를 포함할지 여부
            .maxAge(3600); // pre-flight 요청의 캐시 시간 (초 단위)
    }
}
