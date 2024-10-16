package com.tboostai_batch.component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tboostai_batch.service.RedisService;
import com.tboostai_batch.util.CommonTools;
import com.tboostai_batch.util.WebClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static com.tboostai_batch.common.GeneralConstants.*;

@Component
public class ProductsSearchComponent {

    private static final Logger logger = LoggerFactory.getLogger(ProductsSearchComponent.class);
    private final WebClientUtils webClientUtils;
    private final RedisService redisService;

    @Value("${EBAY_SEARCH_BY_CATEGORY_ID}")
    private String ebaySearchApiUrl;
    @Value("${EBAY_CAR_TRUCK_US_CA_CATE_ID}")
    private String ebayCarTruckUsCaCateId;
    @Value("${EBAY_HOST}")
    private String ebayHost;

    public ProductsSearchComponent(WebClientUtils webClientUtils, RedisService redisService) {
        this.webClientUtils = webClientUtils;
        this.redisService = redisService;
    }

    public List<String> searchProducts(String accessToken) {

        logger.info("Base URL is {}", ebaySearchApiUrl);

        List<String> allItemIds = new ArrayList<>();
        // Check if there is a URL stored by redis
        String currentUrl = redisService.getNextUrl();
        logger.info("Current URL is {}", currentUrl);
        if (currentUrl == null) {
            currentUrl = UriComponentsBuilder.newInstance()
                    .scheme(HTTPS)
                    .host(ebayHost)
                    .path(ebaySearchApiUrl)
                    .queryParam(CATEGORY_IDS, ebayCarTruckUsCaCateId)
                    .queryParam(LIMIT, EBAY_SEARCH_API_LIMIT_CALL_MAX)
                    .queryParam(OFFSET, 0)
                    .encode()
                    .build()
                    .toUri()
                    .toString();
        }

        HttpHeaders headers = CommonTools.generateEbayBasicHeaders(accessToken);
        Mono<String> ebayResponseStr = webClientUtils.sendGetRequestExternal(currentUrl, String.class, headers, 3, 3);

        try {
            String response = ebayResponseStr.block();

            // 使用 ObjectMapper 解析返回的 JSON
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response);

            if (jsonNode.has("next")) {
                String nextUrl = jsonNode.get("next").asText();
                storeNextUrlToRedis(nextUrl);
            }
            // 获取 items 节点
            if (jsonNode.has("itemSummaries")) {
                JsonNode itemSummaries = jsonNode.get("itemSummaries");
                for (JsonNode itemNode : itemSummaries) {
                    if (itemNode.has("itemId")) {
                        allItemIds.add(itemNode.get("itemId").asText());
                    }
                }
            }

            logger.info("Current item IDs list is {}, total {} items", allItemIds, allItemIds.size());

            storeItemIdsToRedis(allItemIds);

        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage());
        }

        return allItemIds;
    }

    private void storeItemIdsToRedis(List<String> allItemIds) {
        if (allItemIds != null && !allItemIds.isEmpty()) {
            redisService.storeCurrentBatch(allItemIds);
        }
    }

    private void storeNextUrlToRedis(String nextUrl) {
        if (nextUrl != null && !nextUrl.isEmpty()) {
            redisService.setNextUrl(nextUrl);
        }

    }
}
