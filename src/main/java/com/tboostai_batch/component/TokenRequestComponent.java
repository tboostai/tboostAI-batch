package com.tboostai_batch.component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tboostai_batch.util.WebClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
public class TokenRequestComponent {

    private static final Logger logger = LoggerFactory.getLogger(TokenRequestComponent.class);

    @Value("${CLIENT_ID}")
    private String CLIENT_ID;
    @Value("${CLIENT_SECRET}")
    private String CLIENT_SECRET;
    @Value("${REFRESH_TOKEN}")
    private String REFRESH_TOKEN;
    @Value("${EBAY_TOKEN_URL}")
    private String EBAY_TOKEN_URL;

    private final WebClientUtils webClientUtils;

    @Autowired
    public TokenRequestComponent(WebClientUtils webClientUtils) {
        this.webClientUtils = webClientUtils;
    }

    public String requestAccessToken() {
        logger.info("Fetching Ebay access token");
        String rawAuthToken = CLIENT_ID.concat(":").concat(CLIENT_SECRET);

        String BASIC_AUTH = "Basic ".concat(encodeBase64(rawAuthToken));
        // Build request header
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Authorization", BASIC_AUTH);

        String body = "grant_type=refresh_token&refresh_token=" + REFRESH_TOKEN;

        Mono<String> responseMono = webClientUtils.sendExternalPostRequest(
                EBAY_TOKEN_URL,
                body,
                headers,
                String.class,
                3,
                5
        );

        try {
            String response = responseMono.block();

            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode jsonNode = objectMapper.readTree(response);

            String accessToken = jsonNode.get("access_token").asText();

            logger.info("Access Token: {}", accessToken);

            return accessToken;
        } catch (Exception e) {
            logger.error("Get Access Token Failed: {}", e.getMessage());
            return null;
        }
    }

    private static String encodeBase64(String value) {
        return java.util.Base64.getEncoder().encodeToString(value.getBytes());
    }
}
