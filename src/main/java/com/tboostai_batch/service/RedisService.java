package com.tboostai_batch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tboostai_batch.entity.ebay.dto.EbayRespBasicDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.tboostai_batch.common.GeneralConstants.ONE_YEAR_IN_SECONDS;

@Service
public class RedisService {
    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);

    private static final String NEXT_URL_KEY = "next_url";  // Store next URL for next API call
    private static final String PROCESSED_ITEMS_SET_KEY = "processed_items";  // 全局Set，存储所有已处理过的itemId
    private static final String CURRENT_BATCH_LIST_KEY = "current_batch_items";   // 当前批次List，存储当前批次的200个itemId
    private static final String TEMP_ITEM_DETAILS = "temp_item_details";   // Redis Hash, 存储每个itemId对应的item详情

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public RedisService(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    // -------------------
    // 功能1：存取nextURL（用于offset）
    // -------------------

    // 保存nextURL（例如，用于存储API的offset）
    public void setNextUrl(String nextUrl) {
        redisTemplate.opsForValue().set(NEXT_URL_KEY, nextUrl);
    }

    // 获取当前的nextURL
    public String getNextUrl() {
        return redisTemplate.opsForValue().get(NEXT_URL_KEY);
    }

    // -------------------
    // 功能2：存取item IDs to Set
    // -------------------

    // 存储当前批次的itemId，并过滤掉已处理的itemId
    public void storeCurrentBatch(List<String> itemIds) {
        // 使用Redis的Set自动去重
        redisTemplate.opsForSet().add(PROCESSED_ITEMS_SET_KEY, itemIds.toArray(new String[0]));
        redisTemplate.expire(PROCESSED_ITEMS_SET_KEY, ONE_YEAR_IN_SECONDS, TimeUnit.SECONDS);  // 设置Set的过期时间为1年

        // 将所有itemId存入当前批次的List中
        redisTemplate.delete(CURRENT_BATCH_LIST_KEY);  // 清空之前的批次
        redisTemplate.opsForList().rightPushAll(CURRENT_BATCH_LIST_KEY, itemIds);
        redisTemplate.expire(CURRENT_BATCH_LIST_KEY, ONE_YEAR_IN_SECONDS, TimeUnit.SECONDS);  // 设置List的过期时间为1年
    }

    // 从Redis中获取当前批次的200个itemId
    public List<String> getCurrentBatch() {
        return redisTemplate.opsForList().range(CURRENT_BATCH_LIST_KEY, 0, -1);
    }

    // 删除单个itemId（从全局Set中删除）
    public void removeItemId(String itemId) {
        redisTemplate.opsForSet().remove(PROCESSED_ITEMS_SET_KEY, itemId);
    }

    // -------------------
    // 功能3：存取item详情 (序列化为JSON后存储)
    // -------------------

    // 存储当前批次的200个item详情到Redis的List中（临时存储）
    public void storeItemDetailsInRedis(List<EbayRespBasicDTO> itemDetails) {
        clearItemDetailsFromRedis();  // 每次运行前清空之前存储的数据，确保只存当前批次
        for (EbayRespBasicDTO item : itemDetails) {
            try {
                // 将EbayRespEntityBasic对象序列化为JSON字符串
                String jsonString = objectMapper.writeValueAsString(item);
                // 将序列化的字符串存储到Redis List中
                redisTemplate.opsForList().rightPush(TEMP_ITEM_DETAILS, jsonString);
            } catch (JsonProcessingException e) {
                logger.error("Failed to serialize item details into JSON", e);
            }
        }
    }

    // 从Redis的List中获取当前批次的200个item详情
    public List<EbayRespBasicDTO> getItemDetailsFromRedis() {
        List<String> jsonStrings = redisTemplate.opsForList().range(TEMP_ITEM_DETAILS, 0, -1);
        if (jsonStrings == null || jsonStrings.isEmpty()) {
            logger.warn("No item details found in Redis");
            return List.of();  // 返回空列表以防止空指针异常
        }

        return jsonStrings.stream()
                .map(jsonString -> {
                    try {
                        // 将JSON字符串反序列化为EbayRespEntityBasic对象
                        return objectMapper.readValue(jsonString, EbayRespBasicDTO.class);
                    } catch (JsonProcessingException e) {
                        logger.error("Failed to deserialize item details from JSON", e);
                        return null;  // 如果反序列化失败返回null
                    }
                })
                .filter(Objects::nonNull)  // 过滤掉反序列化失败的null对象
                .collect(Collectors.toList());
    }

    // 清除临时存储的item详情
    public void clearItemDetailsFromRedis() {
        redisTemplate.delete(TEMP_ITEM_DETAILS);
    }
}
