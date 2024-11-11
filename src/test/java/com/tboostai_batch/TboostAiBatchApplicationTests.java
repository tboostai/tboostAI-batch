package com.tboostai_batch;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
@ActiveProfiles("dev")
class TboostAiBatchApplicationTests {

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {

        // 其他应用程序所需的环境变量
        registry.add("SERVER_PORT", () -> "8080");
        registry.add("EUREKA_CONFIG_SERVICE_URL_DEFAULT_ZONE", () -> "http://localhost:8761/eureka");
        registry.add("BATCH_INIT_SCHEMA", () -> "always");
        registry.add("EBAY_CLIENT_ID", () -> "default_client_id");
        registry.add("EBAY_CLIENT_SECRET", () -> "default_client_secret");
        registry.add("EBAY_REFRESH_TOKEN", () -> "default_refresh_token");
        registry.add("TBOOSTAI_LLM_HOST", () -> "http://localhost:8080");
        registry.add("GOOGLE_API_KEY", () -> "default_google_api_key");
        registry.add("EBAY_JOB_SCHEDULED", () -> "false");
        registry.add("SPRING_PROFILES_ACTIVE", () -> "test");
    }

    @Test
    void contextLoads() {
    }
}
